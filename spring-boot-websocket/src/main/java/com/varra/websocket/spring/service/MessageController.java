package com.varra.websocket.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class MessageController {
    @MessageMapping("/sms")
    @SendTo("/topic/sms")
    public ServerMessage messageFromClient(ClientMessage message) throws Exception {
        log.info("Received hello: {}", message.getName());
        return new ServerMessage("[SMS]: " + message.getName() + "!");
    }

    @MessageMapping("/mms")
    @SendTo("/topic/mms")
    public ServerMessage hi(ClientMessage message) throws Exception {
        log.info("Received hello: {}", message.getName());
        return new ServerMessage("[MMS]: " + message.getName() + "!");
    }
}
