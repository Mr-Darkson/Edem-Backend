package com.coursework.edem.EdemBackend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "avatars")
public class Avatar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "avatar_name")
    private String avatarName;

    public Avatar() {
    }

    public Avatar(Person person, String avatarName) {
        this.person = person;
        this.avatarName = avatarName;
    }
}