package com.executor.executor;

import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@RestController
@EnableJms
public class ExecutorApplication {

	@Autowired
	private JmsTemplate jmsTemplate;

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(ExecutorApplication.class, args);

	}

	@GetMapping("send")
	public String send() {
		try{
			jmsTemplate.convertAndSend("TEST.IN2", "Hello World!");
			return "OK";
		}catch(JmsException ex){
			ex.printStackTrace();
			return "FAIL";
		}
	}

}
