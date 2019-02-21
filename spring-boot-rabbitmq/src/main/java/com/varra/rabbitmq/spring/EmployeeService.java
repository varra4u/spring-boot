package com.varra.rabbitmq.spring;

import com.varra.rabbitmq.spring.config.Config;
import com.varra.rabbitmq.spring.producer.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;

import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/employees")
public class EmployeeService {

	private final RabbitTemplate rabbitTemplate;
	private Config config;
	private MessageProducer messageProducer;

	@Autowired
	public EmployeeService(final RabbitTemplate rabbitTemplate, Config config, MessageProducer messageProducer) {
		this.rabbitTemplate = rabbitTemplate;
		this.config = config;
		this.messageProducer = messageProducer;
	}

	@PostMapping
	public void add(@RequestBody Employee employee) {
		messageProducer.publish(rabbitTemplate, config.getExchangeName(), config.getRoutingKey(), employee);
	}

	@PostMapping(path = "/pdfs", consumes = APPLICATION_OCTET_STREAM_VALUE)
	public void add(@RequestBody byte[] data) throws IOException {
		messageProducer.publish(rabbitTemplate, config.getExchangeName(), config.getPdfsRoutingKey(), new Message(data, new MessageProperties()));
	}
}
