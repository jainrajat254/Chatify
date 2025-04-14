package com.example.chatify.controller;

import com.example.chatify.dto.SendMessageRequest;
import com.example.chatify.models.Messages;
import com.example.chatify.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void handleSendMessage(SendMessageRequest messageRequest) {
        Messages savedMessage = messageService.sendMessageAndReturn(messageRequest);
        messagingTemplate.convertAndSendToUser(
                messageRequest.getReceiverId().toString(),
                "/queue/messages",
                savedMessage
        );
    }
}
