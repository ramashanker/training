package com.rama.spring.app.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetController {
	@RequestMapping(value = "/greet", method = RequestMethod.GET)
	@ResponseBody
	public String currentUserName() {
		return "Hello Rama";
	}
	
	@RequestMapping(value = "/greetname", method = RequestMethod.GET)
	@ResponseBody
	public String userName(@RequestParam String name /*@PathParam("name") String name*/) {
		return "Hello"+name;
	}
	
	@RequestMapping(value = "/user-name", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }
	@RequestMapping(value = "/admin-name", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }
	
	
	
}

