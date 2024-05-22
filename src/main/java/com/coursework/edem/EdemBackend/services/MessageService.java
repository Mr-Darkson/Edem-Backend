package com.coursework.edem.EdemBackend.services;

import com.coursework.edem.EdemBackend.models.Message;
import com.coursework.edem.EdemBackend.repositories.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<Message> findAllByReceiverId(Long id) {
        return messageRepository.findAllByReceiverId(id);
    }

    public List<Message> findAllBySenderId(Long id) {
        return messageRepository.findAllBySenderId(id);
    }

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }
    @Transactional
    public void save(Message message) {
        messageRepository.save(message);
    }

}
