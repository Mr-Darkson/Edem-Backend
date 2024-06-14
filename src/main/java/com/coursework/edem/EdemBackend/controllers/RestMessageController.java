package com.coursework.edem.EdemBackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestMessageController {

    @PostMapping("/deleteMessages")
    public ResponseEntity<String> deleteMessages(@RequestBody List<String> messageIds) {
        // Здесь ваша логика удаления сообщений по идентификаторам
        return ResponseEntity.ok().body("Сообщения успешно удалены");
    }
}
