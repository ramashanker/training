package com.kafka.app.message.consume;

import java.time.LocalDate;
import java.util.Date;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics ="${spring.kafka.topic.first}")
    public void consumeFromFirst(ConsumerRecord<?, ?> consumerRecord) {
        LocalDate localDate = LocalDate.now();

        System.out.println("Time in milliseconds using Date class: " + localDate);
        PayloadData data= (PayloadData)consumerRecord.value();
        logger.info("Receiver on topic: "+ consumerRecord.value().toString());
    }
    
    @KafkaListener(topics ="${spring.kafka.topic.second}")
    public void consumeFromSecond(ConsumerRecord<?, ?> consumerRecord) {
    	LocalDate localDate = LocalDate.now();
        System.out.println("Time in milliseconds using Date class: " + localDate);

        logger.info("Receiver on topic: "+ consumerRecord.value().toString());
    }
}