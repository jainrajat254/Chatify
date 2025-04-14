package com.example.chatify.controller;

import com.example.chatify.service.CloudinaryService;
import com.example.chatify.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private CloudinaryService cloudinaryService;

//    @PostMapping("/send")
//    public void sendMessage(@RequestBody SendMessageRequest request) {
//        messageService.sendMessage(request);
//    }

    @GetMapping("/history/{senderId}/{receiverId}")
    public ResponseEntity<?> getChatHistory(@PathVariable UUID senderId, @PathVariable UUID receiverId) {
        return ResponseEntity.ok(messageService.getChatHistory(senderId, receiverId));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadMedia(@RequestParam("file") MultipartFile file) {
        try {
            String url = cloudinaryService.uploadFile(file);
            return ResponseEntity.ok(Map.of("url", url));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }

}
