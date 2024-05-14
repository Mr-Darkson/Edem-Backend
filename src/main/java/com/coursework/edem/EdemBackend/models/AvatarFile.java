package com.coursework.edem.EdemBackend.models;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AvatarFile {
    private MultipartFile multipartFile;

    public AvatarFile() {

    }

    public AvatarFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
