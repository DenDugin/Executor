package com.executor.executor.component;

import com.executor.executor.entity.Message;
import com.executor.executor.service.Writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@EnableRabbit
@Component
public class RabbitMqListener {

    private Logger logger =  LogManager.getLogger();

    @Autowired
    private Writer writer;


    @RabbitListener(queues = "#{queue.getName()}")
    public void receiveMessage(Message message) throws Exception {

        logger.info("receiveMessage");
        writer.writeData(message);
        Thread.sleep(3000);
    }


    @RabbitListener(queues = "#{queue.getName()}", returnExceptions = "false")
    public Boolean receiveMessageAndResponse(Message message) throws Exception {

        logger.info("receiveMessage");
        writer.writeData(message);
        Thread.sleep(3000);
        return true;
    }



}
