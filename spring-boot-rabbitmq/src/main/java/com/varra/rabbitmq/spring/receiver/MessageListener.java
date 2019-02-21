package com.varra.rabbitmq.spring.receiver;

import com.varra.rabbitmq.spring.Employee;
import com.varra.rabbitmq.spring.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

import static java.io.File.createTempFile;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static org.springframework.util.FileCopyUtils.copy;

@Service
@Slf4j
public class MessageListener {

	private Config config;

	@Autowired
	public void setConfig(Config config)
	{
		this.config = config;
	}

	@RabbitListener(queues = "${queue.name}")
    public void onMessage(final Employee employee)
	{
    	log.info("Received message from Queue: {}, processing it.", employee);
    	if (config.getProcessingTime() > 0)
		{
			try
			{
				TimeUnit.SECONDS.sleep(config.getProcessingTime());
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
    	log.info("Processing of the received message has been completed.");
    }

    @RabbitListener(queues = "${pdfs.queue.name}")
    public void onMessage(final byte[] pdf)
	{
    	log.info("Received a pdf from Queue: {}, processing it.", pdf.length);
    	if (config.getProcessingTime() > 0)
		{
			try
			{
				TimeUnit.SECONDS.sleep(config.getProcessingTime());
				copy(pdf, createTempFile("test", ".pdf", get("./").toAbsolutePath().toFile()));
			}
			catch (Exception e)
			{
				log.error("Error during the message processing, not sending the ack.");
				throw new RuntimeException("Please try to deliver it again!!");
			}
		}
    	log.info("Processing of the received message has been completed.");
    }
}
