package com.coursework.edem.EdemBackend.controllers;

import com.coursework.edem.EdemBackend.models.AvatarFile;
import com.coursework.edem.EdemBackend.models.Message;
import com.coursework.edem.EdemBackend.models.Person;
import com.coursework.edem.EdemBackend.models.PersonProfileData;
import com.coursework.edem.EdemBackend.security.PersonDetails;
import com.coursework.edem.EdemBackend.services.FileService;
import com.coursework.edem.EdemBackend.services.MessageService;
import com.coursework.edem.EdemBackend.services.PersonService;
import com.coursework.edem.EdemBackend.util.FileValidator;
import com.coursework.edem.EdemBackend.utils.PersonProfileDataValidator;
import jakarta.servlet.http.HttpServletResponse;
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
    private final PersonProfileDataValidator personProfileDataValidator;
    private final FileValidator fileValidator;
    private final FileService fileService;

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        Person person = personService.getPersonById(personDetails.getPerson().getId());
        PersonProfileData personProfileData = new PersonProfileData();
        personProfileData.setUsername(person.getUsername());
        //пароль не передаём в форму в целях безопасности

        System.out.println(System.getProperty("user.dir"));

        model.addAttribute("person", personDetails.getPerson());
        model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
        model.addAttribute("personProfileData", personProfileData);

        return "account/messages/profile";
    }

    @PatchMapping("/profile")
    public String updatePersonData(@AuthenticationPrincipal PersonDetails personDetails, Model model, @ModelAttribute("personProfileData") @Valid PersonProfileData personProfileData, BindingResult bindingResult) {
        if (!personProfileData.getAvatarFile().isEmpty()) {
            personProfileDataValidator.validate(personProfileData, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("person", personDetails.getPerson());
            model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));

            return "account/messages/profile";
        }

        personService.updatePerson(personDetails.getPerson().getId(), personProfileData);
        return "redirect:/service/profile";
    }

    @GetMapping("/mailbox")
    public String mailbox(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("person", personDetails.getPerson());
        List<Message> messages = messageService.findAllByReceiverId(personDetails.getPerson().getId()).reversed();
        model.addAttribute("messages", messages);
        model.addAttribute("filesToUpload", new AvatarFile());
        return "account/messages/mailbox";
    }

    @PostMapping("/mailbox") // sendmessage
    public String sendMessagePost(@AuthenticationPrincipal PersonDetails personDetails, @ModelAttribute("filesToUpload") MultipartFile[] multipartFile, @RequestParam String login, @RequestParam String title, @RequestParam String message_text, Model model) {
        var messageGetter = personService.getPersonByLogin(login);
        if (messageGetter.isPresent()) {
            Message message = new Message(messageGetter.get().getId(), personDetails.getPerson().getId(), messageGetter.get().getLogin(), personDetails.getPerson().getLogin(), title, message_text);
            messageService.save(message);
            fileService.uploadFilesToServer(multipartFile, message.getId());
        }
        return "redirect:/service/mailbox";
    }

    @GetMapping("/sent")
    public String sent(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("person", personDetails.getPerson());
        List<Message> messages = messageService.findAllBySenderId(personDetails.getPerson().getId()).reversed();
        model.addAttribute("messages", messages);
        return "account/messages/sent";
    }

    @GetMapping("/bin")
    public String bin(){
        return "account/messages/bin";
    }
    @GetMapping("/message/{id}")
    public String currentMessage(@AuthenticationPrincipal PersonDetails personDetails, Model model, @PathVariable Long id) {
        var message = messageService.findById(id);
        if (message.isPresent()) {
            if (message.get().getReceiverId() == personDetails.getPerson().getId()) {
                model.addAttribute("Email", message.get().getSenderLogin());
            } else {
                model.addAttribute("Email", message.get().getReceiverLogin());
            }
            model.addAttribute("Message", message.get());
            return "account/messages/sms-write";
        }
        return "account/messages/mailbox";

    }

    @GetMapping("/download/{id}")
    public void downloadFile(HttpServletResponse response, @AuthenticationPrincipal PersonDetails personDetails, @PathVariable Long id) {
        fileService.downloadFilesFromServer(id, response);
    }
}