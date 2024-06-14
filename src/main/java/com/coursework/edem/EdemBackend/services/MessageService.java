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

    public List<Message> searchMailboxByMessageText(String searchText, Long id) {
        return messageRepository.findAllByMessageTextContainingAndReceiverIdOrTitleContainingAndReceiverIdOrSenderLoginContainingAndReceiverId(searchText, id, searchText, id, searchText, id);
    }

    public List<Message> searchSentByMessageText(String searchText, Long id) {
        return messageRepository.findAllByMessageTextContainingAndSenderIdOrTitleContainingAndSenderIdOrReceiverLoginContainingAndSenderId(searchText, id, searchText, id, searchText, id);
    }

    public List<Message> searchBinByMessageText(String searchText, Long id) {
        return messageRepository.findByMessageTextContainingAndSenderIdOrReceiverId(searchText, id, id);
    }

    public List<Message> findAllByReceiverIdAndIsInBin(Long receiverId, Long isInBin){
        return messageRepository.findAllByReceiverIdAndIsInBin(receiverId, isInBin);
    }

    @Transactional
    public void save(Message message) {
        messageRepository.save(message);
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }
}
