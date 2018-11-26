package com.rama.spring.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetController {
	@RequestMapping(value = "/greet", method = RequestMethod.GET)
	@ResponseBody
	public String currentUserName() {
		return "Hello Rama";
	}
}
