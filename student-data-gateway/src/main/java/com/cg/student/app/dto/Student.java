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
	
	public String getRollno() {
		return rollno;
	}

	public void setRollno(String rollno) {
		this.rollno = rollno;
	}
	
	public String getStd() {
		return rollno;
	}

	public void setStd(String std) {
		this.std = std;
	}


	@Override
	public String toString() {
		return ", fullName: " + this.fullName + ", Rollno: " + this.rollno + ", Standard: " + this.std;
	}
}