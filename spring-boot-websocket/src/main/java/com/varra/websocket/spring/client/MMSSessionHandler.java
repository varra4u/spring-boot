package com.varra.websocket.spring.client;

import com.varra.websocket.spring.service.ServerMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Data
@AllArgsConstructor
@Slf4j
public class MMSSessionHandler extends StompSessionHandlerAdapter {

    private final String topic;
    private final String endpoint;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe(topic, this);
        session.send(endpoint, "{\"name\":\"Client\"}".getBytes());

        log.info("New session: {}", session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return ServerMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Received a new message: {}", ((ServerMessage) payload).getContent());
    }
}
