package com.executor.executor;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class ExecutorApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(ExecutorApplication.class, args);
	}

}
