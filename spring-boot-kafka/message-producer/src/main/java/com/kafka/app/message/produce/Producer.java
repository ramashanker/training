package com.kafka.app.message.produce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Value("${spring.kafka.topic}")
    protected String destinationTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String payload){
        logger.info("Message: "+payload+" sent to topic: "+destinationTopic);
        kafkaTemplate.send(destinationTopic, payload);
    }

}