package com.rama.mongo.operation.service;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.rama.mongo.operation.document.Employee;

@Service
public class DataOperationServiceImpl implements DataOperationService {

	private MongoTemplate mongoTemplate;

	public DataOperationServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	@Override
	public Employee createData(Employee employee) {
		employee.set_id(ObjectId.get());
		if(employee.getJoiningDate()==null) {
			Date date = new Date();
			employee.setJoiningDate(date);
		}
		mongoTemplate.insert(employee);
		return employee;
	}

	@Override
	public List<Employee> readAllData() {
		return mongoTemplate.findAll(Employee.class);
	}

	@Override
	public Employee updateData(Employee employee) {
		mongoTemplate.save(employee);
		return employee;
	}

	@Override
	public void deleteData(ObjectId  id) {
		mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Employee.class);
	}
}
