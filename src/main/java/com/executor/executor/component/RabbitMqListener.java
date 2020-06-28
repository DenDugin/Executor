package com.executor.executor.component;

import com.executor.executor.config.RabbitConfiguration;
import com.executor.executor.entity.Order;
import com.executor.executor.service.WriteData;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableRabbit
@Component
public class RabbitMqListener {

    Logger logger = Logger.getLogger(RabbitMqListener.class);

    @Autowired
    private WriteData writeData;

    @RabbitListener(queues = "#{queue.getName()}")
    public void receiveMessage(Order order) throws InterruptedException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date date = new Date();

        logger.info("receiveMessage :" + dateFormat.format(date));

        writeData.write(order);

        Thread.sleep(3000);

        // return "Done";
    }


}
