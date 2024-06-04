package com.coursework.edem.EdemBackend.models;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PersonProfileData {
    private MultipartFile avatarFile;

    @Pattern(regexp = "^[a-zA-Z0-9-_)(]{3,16}$", message =
            "Никнейм должен быть одним словом, содержать только латинские буквы, цифры 0-9 и содержать от 3 до 16 символов. Разрешённые специальные символы: ( , ) , _ , - .")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9-_)(]*$", message =
            "Пароль должен быть одним словом, содержать только латинские буквы и цифры 0-9. Разрешённые специальные символы: ( , ) , _ , - .")
    private String password;

    public PersonProfileData() {

    }

    public PersonProfileData(MultipartFile avatarFile, String username, String password) {
        this.avatarFile = avatarFile;
        this.username = username;
        this.password = password;
    }
}
