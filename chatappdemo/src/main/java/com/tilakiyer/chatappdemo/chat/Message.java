package com.tilakiyer.chatappdemo.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class Message {
    @Getter @Setter
    private String content;
    
    @Getter @Setter
    private String sender;
}
