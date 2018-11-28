package com.cg.student.app.dto;
public class Student {

	
	private String fullName;
	private String rollno;
	private String std;

	

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return  ", fullName: " + this.fullName + ", Rollno: " + this.rollno + ", Standard: " + this.std;
	}
}