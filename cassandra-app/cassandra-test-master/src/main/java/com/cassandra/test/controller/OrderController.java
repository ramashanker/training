package com.cassandra.test.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cassandra.test.model.SaveRequest;
import com.cassandra.test.service.OrderService;

@RestController
public class OrderController {

	private static Logger log = Logger.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/order/save", method = RequestMethod.POST)
	public boolean cprEligibilitycheck(@RequestBody SaveRequest request) {
		log.info("inside save request controller.");
		return orderService.save(request);

	}
}
