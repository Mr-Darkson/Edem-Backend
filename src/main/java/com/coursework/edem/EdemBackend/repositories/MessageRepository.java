package com.coursework.edem.EdemBackend.repositories;


import com.coursework.edem.EdemBackend.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySenderId(Long senderId);

    List<Message> findAllByReceiverId(Long receiverId);

    List<Message> findByMessageTextContainingAndReceiverId(String searchText, Long receiverId);

    List<Message> findByMessageTextContainingAndSenderId(String searchText, Long receiverId);

    List<Message> findByMessageTextContainingAndSenderIdOrReceiverId(String searchText, Long senderId, Long receiverId);
}
