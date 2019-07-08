package com.mongo.app.student.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongo.app.student.document.Student;

public interface DataOperationService {
	Student createData(Student employee);
	List<Student> readAllData();
	Student updateData(Student employee);
	void deleteData(ObjectId employee);
}
