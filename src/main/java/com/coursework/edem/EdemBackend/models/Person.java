package com.coursework.edem.EdemBackend.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Transient
    private String repeatPassword;

    @Column(name = "createdat")
    private Date createdAt;

    @Column(name = "last_login")
    private Date lastLogin;

    public Person(String login, String password, Date createdAt, byte[] avatar, Date lastLogin) {
        this.login = login;
        this.password = password;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }
    public Person(){}

}