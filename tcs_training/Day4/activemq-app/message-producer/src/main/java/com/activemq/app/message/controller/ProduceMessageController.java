package com.activemq.app.message.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kafka.app.message.Producer;

@RestController
@RequestMapping(value = "/publish")
public class ProduceMessageController {
    private final Logger logger = LoggerFactory.getLogger(DataController.class);
    private final JmsProducer producer;

    @Autowired
    ProduceMessageController(JmsProducer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/produce")
    public void sendMessage(@RequestBody String message) {
        logger.info("send message to activemq");
         producer.sendMessage( message);
    }

}
