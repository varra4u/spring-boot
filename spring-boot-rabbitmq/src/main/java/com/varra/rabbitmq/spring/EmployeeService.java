package com.varra.rabbitmq.spring;

import com.varra.rabbitmq.spring.config.Config;
import com.varra.rabbitmq.spring.producer.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/employees")
public class EmployeeService {

	private final RabbitTemplate rabbitTemplate;
	private Config config;
	private MessageProducer messageProducer;

//	@Autowired
//	public void setApplicationConfig(Config config) {
//		this.config = config;
//	}

	@Autowired
	public EmployeeService(final RabbitTemplate rabbitTemplate, Config config, MessageProducer messageProducer) {
		this.rabbitTemplate = rabbitTemplate;
		this.config = config;
		this.messageProducer = messageProducer;
	}

//	@Autowired
//	public void setMessageSender(MessageProducer messageProducer) {
//		this.messageProducer = messageProducer;
//	}

	@PostMapping
	public void add(@RequestBody Employee employee) {
		messageProducer.publish(rabbitTemplate, config.getExchangeName(), config.getRoutingKey(), employee);
	}
}
