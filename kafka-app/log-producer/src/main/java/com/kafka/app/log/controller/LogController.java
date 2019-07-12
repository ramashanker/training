package com.kafka.app.log.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.app.log.LogApplication;

@RestController
public class LogController {
	 private static final Logger logger = LoggerFactory.getLogger(LogApplication.class);
	@RequestMapping("/")
    public String index() {
		logger.info("Log sent to kafka!!");
        return "Log sent to kafka!!";
    }

}
