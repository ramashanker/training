package com.kafka.app.stream;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LogApplication {
	 private static final Logger logger = LoggerFactory.getLogger(LogApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
        logger.info("application started");
    }
}