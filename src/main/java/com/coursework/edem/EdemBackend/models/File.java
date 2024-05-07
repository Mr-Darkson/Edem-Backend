package com.coursework.edem.EdemBackend.models;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Table(name = "file")
public class File {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "message_id")
    private Long message_id;
    @Column(name = "file")
    private byte[] file;

    public File(Long id, Long message_id, byte[] file) {
        this.id = id;
        this.message_id = message_id;
        this.file = file;
    }

    public File(){}

}
