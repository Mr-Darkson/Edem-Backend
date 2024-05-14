package com.coursework.edem.EdemBackend.models;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
@Table(name = "message")
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "receiver_id")
    private Long receiverId;
    @Column(name = "sender_id")
    private Long senderId;
    @Column(name = "title")
    private String title;

    @Column(name = "message_text")
    private String message_text;

    public Message(Long receiver_id, Long sender_id, String title, String message_text) {
        this.receiverId = receiver_id;
        this.senderId = sender_id;
        this.title = title;
        this.message_text = message_text;
    }

    public Message() {
    }

}
