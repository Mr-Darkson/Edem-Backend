package com.coursework.edem.EdemBackend.controllers;

import com.coursework.edem.EdemBackend.models.Message;
import com.coursework.edem.EdemBackend.models.Person;
import com.coursework.edem.EdemBackend.repositories.MessageRepository;
import com.coursework.edem.EdemBackend.repositories.PersonRepository;
import com.coursework.edem.EdemBackend.security.PersonDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/service")
public class MessageController {
    
    private final MessageRepository messageRepository;

    private final PersonRepository personRepository;




    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("person", personDetails.getPerson());
        return "account/messages/profile";
    }

    @GetMapping("/mailbox")
    public String mailbox(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("person", personDetails.getPerson());
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "account/messages/mailbox";
    }

    @GetMapping("/sendmessage")
    public String sendMessage(Model model){
        return "account/messages/sendmessage";
    }

    @PostMapping("/sendmessage")
    public String sendMessagePost(@RequestParam Long id, @RequestParam String title, @RequestParam String message_text, Model model){
        Message message = new Message(id, 1L, title, message_text);
        messageRepository.save(message);
        return "redirect:/mailbox";
    }
}
