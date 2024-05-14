package com.coursework.edem.EdemBackend.controllers;


import com.coursework.edem.EdemBackend.models.Avatar;
import com.coursework.edem.EdemBackend.repositories.AvatarRepository;
import com.coursework.edem.EdemBackend.services.AvatarService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @GetMapping("/avatar/{id}")
    private ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        File file = avatarService.getAvatarByPersonId(id);

        if (file.exists()) {
            byte[] imageBytes = new byte[(int) file.length()];
            try (InputStream inputStream = new FileInputStream(file)) {
                inputStream.read(imageBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
