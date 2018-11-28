package com.cg.student.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.student.app.dto.Student;

@RestController
public class StudentController {
	@RequestMapping(value = "/createData", method = RequestMethod.POST)
	@ResponseBody
	public Student createStudentData(@RequestBody Student student) {
		return student;
	}
	
	@RequestMapping(value = "/readData", method = RequestMethod.GET)
	@ResponseBody
	public List<Student> readStudentData() {
		return null;
	}
}
