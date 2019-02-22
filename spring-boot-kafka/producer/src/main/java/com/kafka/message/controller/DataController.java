package com.kafka.message.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.message.produce.Producer;

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
    public void sendMessage(@RequestParam("message") String message) {
        logger.info("send message to kafka topic");
        this.producer.send( message);
    }

    @PostMapping(value = "/{topicName}")
    public void sendMessageToKafkaTopic(@PathVariable String topicName,@RequestParam("message") String message) {
        logger.info("send message to kafka topic",topicName);
        this.producer.sendToTopic(topicName,  message);
    }
}