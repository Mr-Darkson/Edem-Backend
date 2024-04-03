package com.coursework.edem.EdemBackend.controllers;

import com.coursework.edem.EdemBackend.models.Message;
import com.coursework.edem.EdemBackend.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/mailbox")
    public String mailbox(Model model) {
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
