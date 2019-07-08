package com.mongo.app.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongo.app.document.Student;

public interface StudentDataOperationService {
	Student createData(Student employee);
	List<Student> readAllData();
	Student updateData(Student employee);
	void deleteData(ObjectId employee);
}
