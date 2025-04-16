package com.example.chatify.message;

import com.example.chatify.enums.MessageType;

import java.util.UUID;

public class SendMessageRequest {
    private UUID senderId;
    private UUID receiverId;
    private String content; // Can be text or a file URL
    private MessageType type;

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(UUID receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
