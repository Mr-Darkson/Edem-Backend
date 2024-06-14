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

    List<Message> findAllByReceiverIdAndIsInBin(Long receiverId, Long isInBin);

    List<Message> findAllBySenderIdAndIsInBin(Long receiverId, Long isInBin);

    // поиск по входящим
    List<Message> findAllByMessageTextContainingAndReceiverIdOrTitleContainingAndReceiverIdOrSenderLoginContainingAndReceiverId(String searchText1, Long receiverId1, String searchText2, Long receiverId2, String searchText3, Long receiverId3);

    // поиск по отправленным
    List<Message> findAllByMessageTextContainingAndSenderIdOrTitleContainingAndSenderIdOrReceiverLoginContainingAndSenderId(String searchText1, Long senderId1, String searchText2, Long senderId2, String searchText3, Long senderId3);

    // поиск по корзине
    List<Message> findByMessageTextContainingAndSenderIdOrReceiverId(String searchText, Long senderId, Long receiverId);
}
