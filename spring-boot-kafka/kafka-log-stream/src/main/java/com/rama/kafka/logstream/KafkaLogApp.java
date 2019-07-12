package com.rama.kafka.logstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaLogApp {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(KafkaLogApp.class, args);
		LogProducer producer = new LogProducer();
		producer.log();
	}
}