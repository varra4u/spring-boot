package com.varra.websocket.spring.client;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;

public class MMSServiceClient {
        private static final String URL = "ws://127.0.0.1:8080/mms";
        private static final String TOPIC = "/topic/mms";
        private static final String ENDPOINT = "/app/mms";

    public static void main(String... argv) {
        final WebSocketClient webSocketClient = new StandardWebSocketClient();
        final WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        final StompSessionHandler sessionHandler = new MMSSessionHandler(TOPIC, ENDPOINT);
        stompClient.connect(URL, sessionHandler);

        System.out.println("Type quit and hit enter anytime!!");
        new Scanner(System.in).nextLine(); //Don't close immediately.
    }
}
