package com.kafka.app.object;

import java.time.LocalDate;
import java.util.concurrent.*;

import com.kafka.app.message.PayloadData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics ="${spring.kafka.topic.first}",containerFactory = "kafkaListenerContainerFactory")
    public void consumeFromFirst(/*ConsumerRecord<?, ?> consumerRecord*/PayloadData data, Acknowledgment acknowledgment, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key) throws InterruptedException, ExecutionException {
        logger.info("Receiver on topic: "+ data.toString());
        logger.info("Receiver on partition key: "+ key);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        CompletableFuture<String> future=calculateAsync(acknowledgment,data.getKey(),executorService);
       // String result= future.get();
       // logger.info("Result:."+result);
    }

    private CompletableFuture<String> calculateAsync(Acknowledgment acknowledgment,String key, ExecutorService executorService) {

        return CompletableFuture.supplyAsync( () -> {
            try {
                long start_time=System.currentTimeMillis();
                logger.info("Task execution started in thread."+Thread.currentThread().getName());
                Thread.sleep(20000);
                acknowledgment.acknowledge();
                long end_time=System.currentTimeMillis();
                logger.info("Time for ack in milliseconds : " + (end_time-start_time) +": for the key:" + key +": In thread:"+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return key;
        }, executorService);
    }
}