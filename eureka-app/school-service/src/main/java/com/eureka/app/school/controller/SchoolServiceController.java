package com.eureka.app.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/getSchoolDetails")
public class SchoolServiceController {
	@Autowired
	RestTemplate restTemplate;

	@GetMapping(value = "/{schoolname}")
	public String getStudents(@PathVariable("schoolname") String schoolname) {
		System.out.println("Getting School details for " + schoolname);
		String requestUrl="http://student-service/getStudentDetailsForSchool/"+schoolname;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
	
		ResponseEntity<String> response = restTemplate.exchange(requestUrl,
				HttpMethod.GET, httpEntity,  String.class);
		 String student=response.getBody();

		System.out.println("Response Received as " + student);

		return "School Name -  " + schoolname + " \n Student Details " + student;
	}

}
