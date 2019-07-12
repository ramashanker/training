package com.rama.kafka.logstream;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogProducer {
	private static final Logger logger = LoggerFactory.getLogger(LogProducer.class);

    public void log() throws InterruptedException {
        while(true) {
            logger.info("Inside scheduleTask - Sending logs to Kafka at " + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
            Thread.sleep(3000);
        }
    }

}
