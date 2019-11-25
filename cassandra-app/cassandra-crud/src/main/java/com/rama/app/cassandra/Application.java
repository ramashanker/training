package com.rama.app.cassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories(basePackages = "com.rama.app.cassandra")
public class Application 
{
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
