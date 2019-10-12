package com.rama.socket.server.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Server {

	@Autowired
	SocketCreate socketCreate;

	public static void main(String args[]) {
		SpringApplication.run(Server.class, args);
	}
	
	@PostConstruct
    private void init() {
		socketCreate.startServer();
    }
}