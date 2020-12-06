package com.kafka.app.message.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kafka.app.message.Producer;

@RestController
@RequestMapping(value = "/publish")
public class DataController {
    private final Logger logger = LoggerFactory.getLogger(DataController.class);
    private final Producer producer;

    @Autowired
    DataController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/data")
    public void sendMessage(@RequestBody String message) {
        logger.info("send message to kafka topic");
        this.producer.send( message);
    }

}