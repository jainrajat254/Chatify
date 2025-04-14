package com.example.chatify.service;

import com.example.chatify.dto.SendMessageRequest;
import com.example.chatify.enums.MessageType;
import com.example.chatify.models.Chat;
import com.example.chatify.models.Messages;
import com.example.chatify.repository.ChatRepository;
import com.example.chatify.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    public void sendMessage(SendMessageRequest request) {
        // Check if a chat already exists between the users
        Optional<Chat> chatOptional = chatRepository.findChatBetweenUsers(request.getSenderId(), request.getReceiverId());

        Chat chat = chatOptional.orElseGet(() -> {
            Chat newChat = new Chat();
            newChat.setUser1Id(request.getSenderId());
            newChat.setUser2Id(request.getReceiverId());
            return chatRepository.save(newChat);
        });

        // Create and save the message
        Messages message = new Messages();
        message.setSenderId(request.getSenderId());
        message.setReceiverId(request.getReceiverId());
        message.setContent(request.getContent());
        message.setType(request.getType());
        message.setTimestamp(LocalDateTime.now());
        message.setChat(chat); // Assign chat

        messageRepository.save(message);

        // WebSocket real-time message push
        messagingTemplate.convertAndSendToUser(
                request.getReceiverId().toString(),
                "/queue/messages",
                message
        );
    }

    public Messages sendMessageAndReturn(SendMessageRequest request) {
        Optional<Chat> chatOptional = chatRepository
                .findChatBetweenUsers(request.getSenderId(), request.getReceiverId());

        Chat chat = chatOptional.orElseGet(() -> {
            Chat newChat = new Chat();
            newChat.setUser1Id(request.getSenderId());
            newChat.setUser2Id(request.getReceiverId());
            return chatRepository.save(newChat);
        });

        Messages message = new Messages();
        message.setSenderId(request.getSenderId());
        message.setReceiverId(request.getReceiverId());
        message.setContent(request.getContent());
        message.setType(MessageType.TEXT);
        message.setTimestamp(LocalDateTime.now());
        message.setChat(chat);

        return messageRepository.save(message);
    }

    public List<Messages> getChatHistory(UUID senderId, UUID receiverId) {
        return messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
                senderId, receiverId, senderId, receiverId
        );
    }

}
