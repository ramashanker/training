/*package com.rama.kafka.logstream;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class Producer {

	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		KafkaProducer kafkaProducer = new KafkaProducer(properties);
		try {
			for (int i = 0; i < 100; i++) {
				System.out.println(i);
				kafkaProducer.send(new ProducerRecord("log-stream", Integer.toString(i), "test message - " + i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			kafkaProducer.close();
		}
	}
}*/