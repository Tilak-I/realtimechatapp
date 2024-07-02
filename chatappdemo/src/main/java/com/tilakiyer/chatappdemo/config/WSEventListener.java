package com.tilakiyer.chatappdemo.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.tilakiyer.chatappdemo.chat.Message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("null")
public class WSEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleConnections(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User Connected: {}", username);
            Message message = Message.builder()
                                     .sender("SYSTEM")
                                     .content("User Connected: " + username)
                                     .build();
            messagingTemplate.convertAndSend("/chatroom", message);
        }
    }

    @EventListener
    public void handleDisconnections(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User Disconnected: {}", username);
            Message message = Message.builder()
                                     .sender("SYSTEM")
                                     .content("User Disconnected: " + username)
                                     .build();
            messagingTemplate.convertAndSend("/chatroom", message);
        }
    }
}
