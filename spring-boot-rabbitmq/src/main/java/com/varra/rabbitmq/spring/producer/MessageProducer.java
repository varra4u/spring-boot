package com.varra.rabbitmq.spring.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageProducer {
	public void publish(RabbitTemplate rabbitTemplate, String exchange, String routingKey, Object data) {
		log.info("Publishing the message to the queue using routingKey {}. Message= {}", routingKey, data instanceof Message ? ((Message) data).getBody().length : data);
		rabbitTemplate.convertAndSend(exchange, routingKey, data);
		log.info("The message has been published to the queue.");
	}
}
