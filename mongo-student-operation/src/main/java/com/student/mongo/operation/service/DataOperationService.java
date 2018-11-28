package com.student.mongo.operation.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.student.mongo.operation.document.Student;

public interface DataOperationService {
	Student createData(Student employee);
	List<Student> readAllData();
	Student updateData(Student employee);
	void deleteData(ObjectId employee);
}
