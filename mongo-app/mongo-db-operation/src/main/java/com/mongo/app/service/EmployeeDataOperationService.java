package com.mongo.app.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongo.app.document.Employee;

public interface EmployeeDataOperationService {
	Employee createData(Employee employee);
	List<Employee> readAllData();
	Employee updateData(Employee employee);
	void deleteData(ObjectId employee);
}
