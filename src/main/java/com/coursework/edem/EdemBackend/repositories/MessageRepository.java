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

    // поиск по входящим
    List<Message> findAllByMessageTextContainingAndReceiverIdOrTitleContainingAndReceiverIdOrSenderLoginContainingAndReceiverId(String searchText1, Long receiverId1, String searchText2, Long receiverId2, String searchText3, Long receiverId3);

    // поиск по отправленным
    List<Message> findAllByMessageTextContainingAndSenderIdOrTitleContainingAndSenderIdOrReceiverLoginContainingAndSenderId(String searchText1, Long senderId1, String searchText2, Long senderId2, String searchText3, Long senderId3);

    // поиск по корзине
    List<Message> findByMessageTextContainingAndSenderIdOrReceiverId(String searchText, Long senderId, Long receiverId);

    // Здесь нужен запрос аля findAllByReceiverId Where IsInBin==1
    List<Message> findAllByReceiverIdAndIsInBin(Long receiverId, Long isinBin); // единицу нужно класть в метод вручную при его вызове
}
