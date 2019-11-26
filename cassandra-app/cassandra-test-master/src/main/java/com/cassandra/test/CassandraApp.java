package com.cassandra.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource(value = {
		"application.properties", "config.properties" })
public class CassandraApp  {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CassandraApp.class, args);
	}

}
