package com.eureka.app.school;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class SpringTestConfig {

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
