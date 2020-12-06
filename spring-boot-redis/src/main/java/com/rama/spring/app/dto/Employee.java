package com.rama.spring.app.dto;

import java.util.Date;

public class Employee {

	private String empNo;
	private String fullName;

	private int age;

	private Date joiningsDate;

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getJoiningDate() {
		return joiningsDate;
	}

	public void setJoiningDate(Date joiningsDate) {
		this.joiningsDate = joiningsDate;
	}

	@Override
	public String toString() {
		return ", empNo: " + empNo //
				+ ", fullName: " + this.fullName + ", Age: " + this.age + ", joiningsDate: " + this.joiningsDate;
	}
}