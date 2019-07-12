/*package com.rama.kafka.logstream;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

*//**
 * Unit test for simple App.
 *//*
public class LogConsumer {
	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("group.id", "test-group");
		final int giveUp = 100;   int noRecordsCount = 0;
		KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);
		List topics = new ArrayList();
		topics.add("log-stream");
		kafkaConsumer.subscribe(topics);
		 while (true) {
	            final ConsumerRecords<Long, String> consumerRecords =
	            		kafkaConsumer.poll(1000);
	            if (consumerRecords.count()==0) {
	                noRecordsCount++;
	                if (noRecordsCount > giveUp) break;
	                else continue;
	            }
	            consumerRecords.forEach(record -> {
	                System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
	                        record.key(), record.value(),
	                        record.partition(), record.offset());
	            });
	            kafkaConsumer.commitAsync();
	        }
		 kafkaConsumer.close();
	        System.out.println("DONE");
	}
}
*/