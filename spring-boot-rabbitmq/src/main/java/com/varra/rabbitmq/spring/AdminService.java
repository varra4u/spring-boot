package com.varra.rabbitmq.spring;

import com.varra.rabbitmq.spring.config.Config;
import com.varra.rabbitmq.spring.producer.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/admin")
public class AdminService {

	private Config config;

	@Autowired
	public void setApplicationConfig(Config config) {
		this.config = config;
	}

	@PutMapping
	public void update(@RequestParam int processingTime) {
		if (processingTime > 0) {
			config.setProcessingTime(processingTime);
		}
	}
}
