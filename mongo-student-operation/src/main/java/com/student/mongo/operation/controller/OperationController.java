package com.student.mongo.operation.controller;

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

import com.student.mongo.operation.document.Student;
import com.student.mongo.operation.service.DataOperationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Mongo DB operation controller api", description = "manage mongo crud operation")
@RestController
@RequestMapping("/mongo/student")
public class OperationController {
	private final DataOperationService dataUploadService;

	public OperationController(final DataOperationService dataUploadService) {
		this.dataUploadService = dataUploadService;
	}

	@ApiOperation(value = "insert document to mongodb", response = Student.class)
	@RequestMapping(value = "create", method = POST, produces =  APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Student createData(@Valid @RequestBody Student student) {
		return dataUploadService.createData(student);	
	}
	
	@ApiOperation(value = "get All document to mongodb", response = Student.class)
	@RequestMapping(value = "read", method = GET, produces =  APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Student> getAllData() {
		return dataUploadService.readAllData();	
	}
	
	@ApiOperation(value = "update document to mongodb", response = Student.class)
	@RequestMapping(value = "update/{id}", method = PUT, produces =  APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Student updateData(@PathVariable("id")ObjectId  id,@Valid @RequestBody Student employee) {
		employee.set_id(id);
		return dataUploadService.updateData(employee);	
	}
	
	@ApiOperation(value = "delete document to mongodb")
	@RequestMapping(value = "delete/{id}", method = DELETE, produces =  APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updateData(@PathVariable("id")ObjectId  id) {
		 dataUploadService.deleteData(id);	
	}
}
