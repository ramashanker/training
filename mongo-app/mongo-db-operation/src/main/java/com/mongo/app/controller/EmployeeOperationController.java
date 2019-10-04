package com.mongo.app.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.app.document.Employee;
import com.mongo.app.service.EmployeeDataOperationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Mongo DB operation controller api", description = "manage mongo crud operation")
@RestController
@RequestMapping("/mongo/employee")
public class EmployeeOperationController {
	private final EmployeeDataOperationService employeeDataOperationService;

	public EmployeeOperationController(final EmployeeDataOperationService dataUploadService) {
		this.employeeDataOperationService = dataUploadService;
	}

	@ApiOperation(value = "insert document to mongodb", response = Employee.class)
	@RequestMapping(value = "create", method = POST, produces =  APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Employee createData(@Valid @RequestBody Employee employee) {
		return employeeDataOperationService.createData(employee);	
	}
	
	@ApiOperation(value = "get All document to mongodb", response = Employee.class)
	@RequestMapping(value = "read", method = GET, produces =  APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Employee> getAllData() {
		return employeeDataOperationService.readAllData();	
	}
	
	@ApiOperation(value = "update document to mongodb", response = Employee.class)
	@RequestMapping(value = "update/{id}", method = PUT, produces =  APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Employee updateData(@PathVariable("id")ObjectId  id,@Valid @RequestBody Employee employee) {
		employee.set_id(id);
		return employeeDataOperationService.updateData(employee);	
	}
	
	@ApiOperation(value = "delete document to mongodb")
	@RequestMapping(value = "delete/{id}", method = DELETE, produces =  APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updateData(@PathVariable("id")ObjectId  id) {
		 employeeDataOperationService.deleteData(id);	
	}
}
