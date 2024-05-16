package com.coursework.edem.EdemBackend.controllers;

import com.coursework.edem.EdemBackend.models.AvatarFile;
import com.coursework.edem.EdemBackend.models.Message;
import com.coursework.edem.EdemBackend.security.PersonDetails;
import com.coursework.edem.EdemBackend.services.FileUploadService;
import com.coursework.edem.EdemBackend.services.MessageService;
import com.coursework.edem.EdemBackend.services.PersonService;
import com.coursework.edem.EdemBackend.util.FileValidator;
import com.coursework.edem.EdemBackend.utils.AvatarFileValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/service")
public class MessageController {

    private final MessageService messageService;
    private final PersonService personService;
    private final AvatarFileValidator avatarFileValidator;
    private final FileValidator fileValidator;
    private final FileUploadService fileUploadService;

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

    @GetMapping("/upload")
    public String uploadFiles(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("filesToUpload", new AvatarFile());
        return "account/messages/upload";
    }

    @PostMapping("/upload")
    public String uploadFilesPost(Model model, @ModelAttribute("filesToUpload") MultipartFile[] multipartFile, @AuthenticationPrincipal PersonDetails personDetails) {
        fileUploadService.uploadFilesToServer(multipartFile, personDetails.getPerson().getId());
        return "redirect:/service/upload";
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
            fileUploadService.uploadFilesToServer(multipartFile, message.getId());
        }
        return "redirect:/service/mailbox";
    }
}
