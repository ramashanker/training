package com.student.mongo.operation.document;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Student")
public class Student {

	@Id
	public ObjectId _id;

	
	@Field(value = "Full_Name")
	private String fullName;
	
	@Field(value = "Roll_No")
	private String rollno;
	
	@Field(value = "Standard")
	private String std;

	// ObjectId needs to be converted to string
	public String get_id() {
		return _id.toHexString();
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getRollno() {
		return rollno;
	}

	public void setRollno(String rollno) {
		this.rollno = rollno;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getStd() {
		return rollno;
	}

	public void setStd(String std) {
		this.std = std;
	}

	@Override
	public String toString() {
		return "id:" + this._id + ", RollNo: " + this.rollno
				+ ", fullName: " + this.fullName + ", Std: " + this.std;
	}
}