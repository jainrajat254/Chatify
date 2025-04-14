package com.example.chatify.config;

import com.example.chatify.enums.Status;
import com.example.chatify.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class PresenceEventListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private AuthRepository authRepository;

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        String username = event.getUser().getName();
        authRepository.findByUsername(username).ifPresent(user -> {
            user.setStatus(Status.ONLINE);
            authRepository.save(user);
        });
        messagingTemplate.convertAndSend("/topic/presence", username + " is online");
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        String username = event.getUser().getName();
        authRepository.findByUsername(username).ifPresent(user -> {
            user.setStatus(Status.OFFLINE);
            authRepository.save(user);
        });
        messagingTemplate.convertAndSend("/topic/presence", username + " is offline");
    }

}

