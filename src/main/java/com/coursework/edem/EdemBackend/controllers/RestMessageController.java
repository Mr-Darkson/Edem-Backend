package com.coursework.edem.EdemBackend.controllers;

import com.coursework.edem.EdemBackend.models.Message;
import com.coursework.edem.EdemBackend.security.PersonDetails;
import com.coursework.edem.EdemBackend.services.FileService;
import com.coursework.edem.EdemBackend.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/service/api")
public class RestMessageController {

    private final MessageService messageService;
    private final FileService fileService;
    @PostMapping("/deleteMessages")
    public ResponseEntity<String> deleteMessages(@AuthenticationPrincipal PersonDetails personDetails, @RequestBody Map<String, List<Long>> json) {
        List<Long> ids = json.getOrDefault("ids", null);
        if(ids == null) return ResponseEntity.badRequest().body("Список выделенных сообщений пуст");
        else {
            List<Message> messages = messageService.findMessagesByIds(ids);
            long authUserId = personDetails.getPerson().getId();
            boolean isUserIdPresentInMessages = messages.stream()
                    .anyMatch(message -> message.getSenderId() == authUserId || message.getReceiverId() == authUserId);

            if(isUserIdPresentInMessages) {
                List<Long> toDelete = new ArrayList<>();
                List<Long> toAddInBin = new ArrayList<>();
                messages.forEach(x -> {
                    if(x.getSenderId() == authUserId) {
                        toDelete.add(x.getId());
                    }
                    else if(authUserId == x.getReceiverId()) {
                        if(x.getIsInBin() == 1L) {
                            toDelete.add(x.getId());
                        }
                        else {
                            toAddInBin.add(x.getId());
                        }
                    }
                });

                if(!toDelete.isEmpty()) {
                    fileService.deleteFilesFromServerByIds(toDelete);
                    messageService.deleteMessagesByIds(toDelete);
                }
                if(!toAddInBin.isEmpty()) {
                    messageService.setMessageIsInBinByIds(toAddInBin, 1);
                }

                return ResponseEntity.ok("Success");
            }
            else {
                return ResponseEntity.status(406).body("Status Access Denied");
            }
        }

    }
}
