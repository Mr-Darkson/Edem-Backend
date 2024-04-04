package com.coursework.edem.EdemBackend.models;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
@Table(name = "message")
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "receiver_id")
    private Long receiver_id;
    @Column(name = "sender_id")
    private Long sender_id;
    @Column(name = "title")
    private String title;

    @Column(name = "message_text")
    private String message_text;

    public Message(Long receiver_id, Long sender_id, String title, String message_text) {
        this.receiver_id = receiver_id;
        this.sender_id = sender_id;
        this.title = title;
        this.message_text = message_text;
    }

    public Message() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(Long receiver_id) {
        this.receiver_id = receiver_id;
    }

    public Long getSender_id() {
        return sender_id;
    }

    public void setSender_id(Long sender_id) {
        this.sender_id = sender_id;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }
}
