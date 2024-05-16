package com.coursework.edem.EdemBackend.controllers;

import com.coursework.edem.EdemBackend.models.AvatarFile;
import com.coursework.edem.EdemBackend.models.Message;
import com.coursework.edem.EdemBackend.security.PersonDetails;
import com.coursework.edem.EdemBackend.services.FileService;
import com.coursework.edem.EdemBackend.services.MessageService;
import com.coursework.edem.EdemBackend.services.PersonService;
import com.coursework.edem.EdemBackend.util.FileValidator;
import com.coursework.edem.EdemBackend.utils.AvatarFileValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/service")
public class MessageController {

    private final MessageService messageService;
    private final PersonService personService;
    private final AvatarFileValidator avatarFileValidator;
    private final FileValidator fileValidator;
    private final FileService fileService;

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("person", personDetails.getPerson());
        model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
        model.addAttribute("avatarFile", new AvatarFile());
        return "account/messages/profile";
    }

    @PatchMapping("/profile")
    public String updatePersonData(@AuthenticationPrincipal PersonDetails personDetails, Model model, @ModelAttribute("avatarFile") @Valid AvatarFile avatarFile, BindingResult bindingResult, @RequestParam("nickname") String nickname) {
        if (!avatarFile.getMultipartFile().isEmpty()) {
            avatarFileValidator.validate(avatarFile, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("person", personDetails.getPerson());
            model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));

            System.out.println("Файл не является изображением");

            return "account/messages/profile";
        }
        personService.updatePerson(personDetails.getPerson().getId(), avatarFile.getMultipartFile(), nickname);
        return "redirect:/service/profile";
    }

    @GetMapping("/mailbox")
    public String mailbox(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("person", personDetails.getPerson());
        List<Message> messages = messageService.findAllByReceiverId(personDetails.getPerson().getId());
        model.addAttribute("messages", messages);
        return "account/messages/mailbox";
    }

    @GetMapping("/sendmessage")
    public String sendMessage(Model model) {
        model.addAttribute("filesToUpload", new AvatarFile());
        return "account/messages/sendmessage";
    }

    @PostMapping("/sendmessage")
    public String sendMessagePost(@AuthenticationPrincipal PersonDetails personDetails, @ModelAttribute("filesToUpload") MultipartFile[] multipartFile, @RequestParam String login, @RequestParam String title, @RequestParam String message_text, Model model) {
        var messageGetter = personService.getPersonByLogin(login);
        if (messageGetter.isPresent()) {
            Message message = new Message(messageGetter.get().getId(), personDetails.getPerson().getId(), title, message_text);
            messageService.save(message);
            fileService.uploadFilesToServer(multipartFile, message.getId());
        }
        return "redirect:/service/mailbox";
    }

    @GetMapping("/download")
    public void downloadFile(HttpServletResponse response) {
        try {
            String dirPath = System.getProperty("user.dir") + "/src/main/data/demo.txt";

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + "1.txt");

            InputStream inputStream = new FileInputStream(dirPath);
            OutputStream outputStream = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
