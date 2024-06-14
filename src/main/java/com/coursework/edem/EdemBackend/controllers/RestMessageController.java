package com.coursework.edem.EdemBackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service/rest")
public class RestMessageController {

    @PostMapping("/deleteMessages")
    public ResponseEntity<String> deleteMessages(@RequestBody List<String> messageIds) {
        messageIds.forEach(System.out::println);
        return ResponseEntity.ok().body("Сообщения успешно удалены");
    }
}
