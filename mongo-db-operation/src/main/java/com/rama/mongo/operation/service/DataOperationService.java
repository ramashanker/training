package com.rama.mongo.operation.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.rama.mongo.operation.document.Employee;

public interface DataOperationService {
	Employee createData(Employee employee);
	List<Employee> readAllData();
	Employee updateData(Employee employee);
	void deleteData(ObjectId employee);
}
