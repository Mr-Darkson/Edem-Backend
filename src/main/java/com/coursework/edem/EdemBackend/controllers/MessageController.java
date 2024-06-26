package com.coursework.edem.EdemBackend.controllers;

import com.coursework.edem.EdemBackend.models.*;
import com.coursework.edem.EdemBackend.security.PersonDetails;
import com.coursework.edem.EdemBackend.services.FileService;
import com.coursework.edem.EdemBackend.services.MessageService;
import com.coursework.edem.EdemBackend.services.PersonService;
import com.coursework.edem.EdemBackend.util.FileValidator;
import com.coursework.edem.EdemBackend.utils.PersonProfileDataValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

        model.addAttribute("person", personDetails.getPerson());
        model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
        model.addAttribute("personProfileData", personProfileData);

        return "account/messages/profile";
    }

    //NEW FO NKULBAKA
    @GetMapping("/profile/change")
    public String changeProfile(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        Person person = personService.getPersonById(personDetails.getPerson().getId());
        PersonProfileData personProfileData = new PersonProfileData();
        personProfileData.setUsername(person.getUsername());

        model.addAttribute("person", personDetails.getPerson());
        model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
        model.addAttribute("personProfileData", personProfileData);
        return "account/messages/change";
    }

    //

    @PatchMapping("/profile")
    public String updatePersonData(@AuthenticationPrincipal PersonDetails personDetails, Model model, @ModelAttribute("personProfileData") @Valid PersonProfileData personProfileData, BindingResult bindingResult) {
        if (!personProfileData.getAvatarFile().isEmpty()) {
            personProfileDataValidator.validate(personProfileData, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("person", personDetails.getPerson());
            model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));

            return "account/messages/change";
        }

        personService.updatePerson(personDetails.getPerson().getId(), personProfileData);
        return "redirect:/service/profile";
    }

    @GetMapping("/mailbox")
    public String mailbox(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("person", personDetails.getPerson());
        List<Message> messages = messageService.findMailboxMessages(personDetails.getPerson().getId()).reversed();
        model.addAttribute("messages", messages);
        model.addAttribute("filesToUpload", new AvatarFile());
        model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
        return "account/messages/mailbox";
    }

    @PostMapping("/mailbox/search")
    public String searchMailbox(@AuthenticationPrincipal PersonDetails personDetails, Model model, @RequestParam("searchInput") String searchText) {
        if (!(searchText == null) || !(searchText.isEmpty())) {
            model.addAttribute("person", personDetails.getPerson());
            List<Message> messages = messageService.searchMailboxByMessageText(searchText, personDetails.getPerson().getId(), 0L);
            model.addAttribute("messages", messages);
            model.addAttribute("filesToUpload", new AvatarFile());
            model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
            return "account/messages/mailbox";
        }

        model.addAttribute("person", personDetails.getPerson());
        List<Message> messages = messageService.findAllByReceiverId(personDetails.getPerson().getId()).reversed();
        model.addAttribute("messages", messages);
        model.addAttribute("filesToUpload", new AvatarFile());
        model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
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
        List<Message> messages = messageService.findSentMessages(personDetails.getPerson().getId()).reversed();
        model.addAttribute("messages", messages);
        model.addAttribute("filesToUpload", new AvatarFile());
        model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
        return "account/messages/sent";
    }


    @PostMapping("/sent/search")
    public String searchSent(@AuthenticationPrincipal PersonDetails personDetails, Model model, @RequestParam("searchInput") String searchText) {
        if (!(searchText == null) || !(searchText.isEmpty())) {
            model.addAttribute("person", personDetails.getPerson());
            List<Message> messages = messageService.searchSentByMessageText(searchText, personDetails.getPerson().getId(), 0L);
            model.addAttribute("messages", messages);
            model.addAttribute("filesToUpload", new AvatarFile());
            model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
            return "account/messages/sent";
        }

        model.addAttribute("person", personDetails.getPerson());
        List<Message> messages = messageService.findAllByReceiverId(personDetails.getPerson().getId()).reversed();
        model.addAttribute("messages", messages);
        model.addAttribute("filesToUpload", new AvatarFile());
        model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
        return "account/messages/sent";
    }

    @GetMapping("/bin")
    public String bin(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("person", personDetails.getPerson());
        List<Message> messages = messageService.findBinMessages(personDetails.getPerson().getId());
        model.addAttribute("messages", messages);
        model.addAttribute("filesToUpload", new AvatarFile());
        model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
        return "account/messages/bin";
    }

    @PostMapping("/bin/search")
    public String searchBin(@AuthenticationPrincipal PersonDetails personDetails, Model model, @RequestParam("searchInput") String searchText) {
        if (!(searchText == null) || !(searchText.isEmpty())) {
            model.addAttribute("person", personDetails.getPerson());
            List<Message> messages = messageService.searchBinByMessageText(searchText, personDetails.getPerson().getId(), 1L);
            model.addAttribute("messages", messages);
            model.addAttribute("filesToUpload", new AvatarFile());
            model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
            return "account/messages/bin";
        }

        model.addAttribute("person", personDetails.getPerson());
        List<Message> messages = messageService.findAllByReceiverId(personDetails.getPerson().getId()).reversed();
        model.addAttribute("messages", messages);
        model.addAttribute("filesToUpload", new AvatarFile());
        model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
        return "account/messages/bin";
    }

    @GetMapping("/message/{id}")
    public String currentMessage(@AuthenticationPrincipal PersonDetails personDetails, Model model, @PathVariable Long id, HttpSession session) {
        var message = messageService.findById(id);
        if (message.isPresent() && (message.get().getSenderId() == personDetails.getPerson().getId() || message.get().getReceiverId() == personDetails.getPerson().getId())) {
            session.setAttribute("message", message.get());
            model.addAttribute("Flag", fileService.isAnyFiles(id));
            model.addAttribute("FlagIsInBin", message.get().getIsInBin() == 1L);
            model.addAttribute("Message", message.get());
            model.addAttribute("filesToUpload", new AvatarFile());
            model.addAttribute("person", personDetails.getPerson());
            model.addAttribute("personData", personService.getPersonById(personDetails.getPerson().getId()));
            return "account/messages/sms-write";
        }
        return "redirect:/service/mailbox";

    }

    @PostMapping("/message/redirect")
    public String sentRedirect(@SessionAttribute("message") Message message, @AuthenticationPrincipal PersonDetails personDetails, @RequestParam String login, @RequestParam String title, @RequestParam String message_text, SessionStatus sessionStatus) {
        Person authPerson = personDetails.getPerson();
        if(message != null && ((authPerson.getId().equals(message.getReceiverId())) || (authPerson.getId().equals(message.getSenderId())) )) {
            Optional<Person> messageGetter = personService.getPersonByLogin(login);
            if(messageGetter.isPresent()) {
                Person res = messageGetter.get();

                Message newMessage = new Message(res.getId(), authPerson.getId(), res.getLogin(), authPerson.getLogin(), title, message_text);
                messageService.save(newMessage);

                Optional<File> fileFromCurrentMessage = fileService.findFileByMessageId(message.getId());
                fileFromCurrentMessage.ifPresent(file ->
                        fileService.saveFileWithName(file.getFilename(), newMessage.getId()));
                sessionStatus.setComplete();
            }

        }
        return "redirect:/service/mailbox";
    }

    @PostMapping("/message/{id}") // sendmessage
    public String currentMessagePost(@AuthenticationPrincipal PersonDetails personDetails, @ModelAttribute("filesToUpload") MultipartFile[] multipartFile, @RequestParam String login, @RequestParam String title, @RequestParam String message_text, Model model) {
        var messageGetter = personService.getPersonByLogin(login);
        if (messageGetter.isPresent()) {
            Message message = new Message(messageGetter.get().getId(), personDetails.getPerson().getId(), messageGetter.get().getLogin(), personDetails.getPerson().getLogin(), title, message_text);
            messageService.save(message);
            fileService.uploadFilesToServer(multipartFile, message.getId());
        }
        return "redirect:/service/message/{id}";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMessage(@AuthenticationPrincipal PersonDetails personDetails, @PathVariable Long id) {
        var message = messageService.findById(id);
        if (message.isPresent() && (message.get().getSenderId() == personDetails.getPerson().getId() || message.get().getReceiverId() == personDetails.getPerson().getId())) {
            var currMessage = message.get();
            if (message.get().getSenderId() == personDetails.getPerson().getId()) {
                fileService.deleteFilesFromServer(currMessage.getId());
                messageService.delete(currMessage);
            } else {
                if (currMessage.getIsInBin() == 1L) {
                    fileService.deleteFilesFromServer(currMessage.getId());
                    messageService.delete(currMessage);
                } else {
                    currMessage.setIsInBin(1L);
                    messageService.save(currMessage);
                }
            }
        }
        return "redirect:/service/mailbox";
    }

    @PatchMapping("/restore/{id}")
    public String restoreMessage(@AuthenticationPrincipal PersonDetails personDetails, Model model, @PathVariable Long id) {
        var message = messageService.findById(id);
        if (message.isPresent() && (message.get().getReceiverId() == personDetails.getPerson().getId())) {
            var currMessage = message.get();
            if (currMessage.getReceiverId() == personDetails.getPerson().getId()) {
                currMessage.setIsInBin(0L);
                messageService.save(currMessage);
            }
        }
        return "redirect:/service/mailbox";
    }

    @GetMapping("/download/{id}")
    public void downloadFile(HttpServletResponse response, @AuthenticationPrincipal PersonDetails personDetails, @PathVariable Long id) {
        var message = messageService.findById(id);
        if (message.isPresent() && (message.get().getSenderId() == personDetails.getPerson().getId() || message.get().getReceiverId() == personDetails.getPerson().getId())) {
            fileService.downloadFilesFromServer(id, response);
        }
    }


}