package com.rama.spring.app.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rama.spring.app.dto.Employee;

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
		return "Hello "+name;
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
	
	@RequestMapping(value = "/createData", method = RequestMethod.POST)
    @ResponseBody
    public Employee createEmployee(@RequestBody Employee employee) {
		final String uri = "http://localhost:8083/mongo/create"; 
	    RestTemplate restTemplate = new RestTemplate();
	    Employee result = restTemplate.postForObject( uri, employee, Employee.class);
	    System.out.println(result);
	    return result;
    }
	@RequestMapping(value = "/readData", method = RequestMethod.POST)
    @ResponseBody
    public List<Employee> getEmployee(@RequestBody Employee employee) {
		final String uri = "http://localhost:8083/mongo/read"; 
	    RestTemplate restTemplate = new RestTemplate();
	    List<Employee> result = restTemplate.getForObject(uri, List.class);
	    System.out.println(result.size());
	    return result;
    }
	
}

