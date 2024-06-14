package com.coursework.edem.EdemBackend.repositories;


import com.coursework.edem.EdemBackend.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySenderId(Long senderId);

    List<Message> findAllByReceiverId(Long receiverId);

    List<Message> findAllByReceiverIdAndIsInBin(Long receiverId, Long isInBin);

    List<Message> findAllBySenderIdAndIsInBin(Long receiverId, Long isInBin);

    List<Message> findAllByIdIn(List<Long> ids);

    void deleteAllByIdIn(List<Long> ids);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.isInBin = ?1 WHERE m.id IN ?2")
    void setMessageIsInBinByIds(int isInBin, List<Long> messageIds);

    // поиск по входящим/корзине
    List<Message> findAllByMessageTextContainingAndReceiverIdAndIsInBinOrTitleContainingAndReceiverIdAndIsInBinOrSenderLoginContainingAndReceiverIdAndIsInBin(
            String searchText1, Long receiverId1, Long isInBin1,
            String searchText2, Long receiverId2, Long isInBin2,
            String searchText3, Long receiverId3, Long isInBin3);

    // поиск по отправленным
    List<Message> findAllByMessageTextContainingAndSenderIdAndIsInBinOrTitleContainingAndSenderIdAndIsInBinOrReceiverLoginContainingAndSenderIdAndIsInBin(
            String searchText1, Long receiverId1, Long isInBin1,
            String searchText2, Long receiverId2, Long isInBin2,
            String searchText3, Long receiverId3, Long isInBin3);
}
