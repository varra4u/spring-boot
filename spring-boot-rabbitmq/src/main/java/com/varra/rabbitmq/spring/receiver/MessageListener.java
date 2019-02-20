package com.varra.rabbitmq.spring.receiver;

import com.varra.rabbitmq.spring.Employee;
import com.varra.rabbitmq.spring.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
}
