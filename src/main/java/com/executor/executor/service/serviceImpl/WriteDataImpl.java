package com.executor.executor.service.serviceImpl;

import com.executor.executor.config.RabbitConfiguration;
import com.executor.executor.entity.Order;
import com.executor.executor.service.WriteData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class WriteDataImpl implements WriteData {

    Logger logger = Logger.getLogger(WriteDataImpl.class);

    @Value("${rabbitmq.routing.key}")
    private String id;

    public void write(Order order) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date date = new Date();

        logger.info("Get message from dispatcher : " + order.getDispatched_id() + "  by : "  +dateFormat.format(date));
        logger.info("Target : " + order.getTarget_id());
        logger.info("dispatched : " + order.getDispatched_id());
        logger.info("Data : " + order.getData());

        try {

            Files.createDirectories( Paths.get( System.getProperty("user.dir")+"/"+id ) );

            File file = new File(System.getProperty("user.dir")+"/"+id+"/"+order.getClient_id()+".xml");


            JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(order, file);


        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }

    }




}

