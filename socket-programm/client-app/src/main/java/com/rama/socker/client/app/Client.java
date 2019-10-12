package com.rama.socker.client.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Client {

	@Autowired
	SocketConnection socketConnection;
	
	public static void main(String args[]) {
		SpringApplication.run(Client.class, args);
		
	}
	
	@PostConstruct
    private void init() {
		socketConnection.connect();

    }
}