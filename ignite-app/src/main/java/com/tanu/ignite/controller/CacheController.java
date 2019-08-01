package com.tanu.ignite.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {
	@RequestMapping("/")
	public String hello() {
		return "Hello World!";
	}
}
