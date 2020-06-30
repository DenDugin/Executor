package com.executor.executor.service.serviceImpl;


import com.executor.executor.entity.Message;
import com.executor.executor.service.Writer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class WriterImpl implements Writer {

    private Logger logger =  LogManager.getLogger();

    @Value("${rabbitmq.routing.key}")
    private String id;

    public void writeData(Message message) {

        logger.info("Get message from dispatcher : " + message.getDispatched_id());
        logger.info("Target : " + message.getTarget_id());
        logger.info("dispatched : " + message.getDispatched_id());
        logger.info("Data : " + message.getData());

        try {

            Files.createDirectories( Paths.get( System.getProperty("user.dir")+"/"+id ) );
            File file = new File(System.getProperty("user.dir")+"/"+id+"/"+message.getClient_id()+".xml");

            JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(message, file);

        } catch (JAXBException | IOException e) {
            logger.error(e.getMessage(),e);
        }

    }




}

