package com.cg.eureka.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringEurekaSchoolClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringEurekaSchoolClientApplication.class, args);
	}
}
