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

    public List<Message> findMailboxMessages(Long id) {
        var messages = messageRepository.findAllByReceiverIdAndIsInBin(id, 0L);
        messages.sort((a, b) -> b.getId().compareTo(a.getId()));
        return messages;
    }

    public List<Message> findSentMessages(Long id) {
        var messages = messageRepository.findAllBySenderIdAndIsInBin(id, 0L);
        messages.sort((a, b) -> b.getId().compareTo(a.getId()));
        return messages;
    }

    public List<Message> findBinMessages(Long id) {
        var messages = messageRepository.findAllByReceiverIdAndIsInBin(id, 1L);
        messages.sort((a, b) -> b.getId().compareTo(a.getId()));
        return messages;
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

    public List<Message> findMessagesByIds(List<Long> ids) {
        return messageRepository.findAllByIdIn(ids);
    }

    @Transactional
    public void deleteMessagesByIds(List<Long> ids) {
        messageRepository.deleteAllByIdIn(ids);
    }

    @Transactional
    public void setMessageIsInBinByIds(List<Long> messageIds, int updateValue) {
        messageRepository.setMessageIsInBinByIds(updateValue, messageIds);
    }

    @Transactional
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Transactional
    public void delete(Message message) {
        messageRepository.delete(message);
    }
}
