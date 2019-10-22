package com.kafka.app.message;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Value("${spring.kafka.topic.first}")
    protected String destinationTopic;

    @Autowired
    @Qualifier("internalKafkaTemplate")
    private KafkaTemplate<String, PayloadData> kafkaTemplate;

    public void send(String key){
        logger.info("Message: "+key+" sent to topic: "+destinationTopic);
        LocalDate localDate=LocalDate.now();
        System.out.println("Time in milliseconds using Date class: " + localDate);
        PayloadData payload= new PayloadData(key,"message:"+key);
        kafkaTemplate.send(destinationTopic,key, payload);
    }

}