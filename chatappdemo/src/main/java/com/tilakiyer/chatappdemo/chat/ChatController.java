package com.tilakiyer.chatappdemo.chat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/chatroom/public")
    public Message sendMessage(Message message) {
        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/chatroom/public")
    public Message addUser(Message message, SimpMessageHeaderAccessor headerAccessor) {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes == null) {
            sessionAttributes = new HashMap<>();
            headerAccessor.setSessionAttributes(sessionAttributes);
        }

        sessionAttributes.put("username", message.getSender());
        return message;
    }
}
