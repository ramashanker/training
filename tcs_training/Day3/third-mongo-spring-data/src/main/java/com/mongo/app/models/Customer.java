package com.mongo.app.models;

public class Customer {
	String name;
	String custId;
	String address;
	public Customer(String name, String custId, String address) {
		super();
		this.name = name;
		this.custId = custId;
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
