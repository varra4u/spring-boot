package com.varra.websocket.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

import static java.time.LocalTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

@Component
@Slf4j
public class MessageUpdater implements ApplicationListener<BrokerAvailabilityEvent> {

    private static final DateTimeFormatter TIME_FORMAT = ofPattern("HH:mm:ss");
    private SimpMessagingTemplate broker;
    private boolean brokerAvailable;

    @Autowired
    public MessageUpdater(final SimpMessagingTemplate broker) {
        this.broker = broker;
    }

    @Scheduled(fixedRate = 5000)
    public void run() {
        if (brokerAvailable) {
            String time = now().format(TIME_FORMAT);

            log.info("Message broadcast: {}", time);
            broker.convertAndSend("/topic/sms", new ServerMessage("[SMS]: Current time is " + time));
            broker.convertAndSend("/topic/mms", new ServerMessage("[MMS]: Current time is " + time));
        }
        else {
            log.warn("Broker is not yet available!");
        }
    }

    @Override
    public void onApplicationEvent(BrokerAvailabilityEvent event) {
        brokerAvailable = event.isBrokerAvailable();
    }
}
