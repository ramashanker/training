package com.splunk.log.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class RequestController {

    private static final Log LOGGER = LogFactory.getLog(RequestController.class);

    @RequestMapping("/message")
    public String index() {
    	LOGGER.info("Log sent to splunk!!");
        return "Log sent to splunk forwarder!!";
    }
}
