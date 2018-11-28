package com.rama.mongo.operation.service;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rama.mongo.operation.document.Employee;

@Service
public class DataOperationServiceImpl implements DataOperationService {

	private MongoTemplate mongoTemplate;

	public DataOperationServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	@Transactional
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
    @Transactional
	@Override
	public List<Employee> readAllData() {
		return mongoTemplate.findAll(Employee.class);
	}
    @Transactional
	@Override
	public Employee updateData(Employee employee) {
		mongoTemplate.save(employee);
		return employee;
	}
	@Transactional
	@Override
	public void deleteData(ObjectId  id) {
		mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Employee.class);
	}
}
