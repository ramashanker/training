package com.kafka.app.message.consume;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics ="${spring.kafka.topic}")
    public void consume(ConsumerRecord<?, ?> consumerRecord) {
        logger.info("Receiver on topic: "+ consumerRecord.value().toString());
    }
}