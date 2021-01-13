package com.executor.executor.component;

import com.sun.org.apache.xpath.internal.operations.String;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@EnableJms
public class IBMMqListener {

    private Logger logger =  LogManager.getLogger();

    @JmsListener(destination = "TEST.IN2")
    public void listener(Message message) throws JMSException {

        logger.info("Receive message");

        TextMessage textMessage = (TextMessage) message;

        logger.info(textMessage.getText());

    }

}
