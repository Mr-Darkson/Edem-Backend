package com.coursework.edem.EdemBackend.controllers;

import com.coursework.edem.EdemBackend.models.Message;
import com.coursework.edem.EdemBackend.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping()
public class MessageController {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/dialogs")
    public String dialogs(Model model) {
        Iterable<Message> dialogs = messageRepository.findAll();
        model.addAttribute("dialogs", dialogs);
        return "account/messages/dialogs";
    }

    @GetMapping("/dialog/{id}")
    public String dialog(@PathVariable(value = "id") long id, Model model) {
        if (!messageRepository.existsById(id)) { // Сюда айди диалога надо сунуть
            return "redirect:/dialogs"; // Тут написать мол диалогов нет
        }
        Optional<Message> message = messageRepository.findById(id); // Сюда айди диалога надо сунуть
        ArrayList<Message> res = new ArrayList<>();
        message.ifPresent(res::add);
        model.addAttribute("message", res);
        return "account/messages/dialog";
    }

    @PostMapping("/dialog/{id}")
    public String blogPostAdd(@PathVariable(value = "id") Long id, @RequestParam String text,  Model model){
        Message message = new Message(id, 0L, 1L, text);
        messageRepository.save(message);
        return "redirect:/dialogs";
    }
}
