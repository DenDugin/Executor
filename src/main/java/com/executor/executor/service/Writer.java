package com.executor.executor.service;


import com.executor.executor.entity.Message;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface Writer {

    void writeData(Message message) throws IOException, JAXBException;

}
