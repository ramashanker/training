package com.mongo.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.app.models.Customer;
import com.mongo.app.models.CustomerRepository;

@RestController
@RequestMapping("/customer")
public class CreateController {
	@Autowired
	CustomerRepository customerRepo;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Customer create(Customer customer) {
		customer = customerRepo.save(customer);
		return customer;
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public List<Customer> readCustomer() {
		return customerRepo.findAll();
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public void modifyByID(Customer customer,@RequestParam String custId, @RequestParam String name) {
		customer = customerRepo.findByName(name);
		customer.setCustId(custId);
		customerRepo.save(customer);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public void deleteByID(@RequestParam String name) {
		customerRepo.deleteByname(name);
	}

}
