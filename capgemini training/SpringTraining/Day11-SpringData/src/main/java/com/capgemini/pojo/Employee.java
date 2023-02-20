package com.capgemini.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;

//JPQL query
@NamedQuery(name="findByDateOfBirth", query="select e from Employee e where e.dateOfBirth = :empDateOfBirth")
@NamedNativeQuery(name="Employee.searchForEmployee", query="select * from employee e where e.salary > :sal", resultClass = Employee.class)
@Entity
public class Employee {

	@Id
	@GeneratedValue
	private int empId;
	
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String email;
	private double salary;
	private String address;
	private String gender;
	//department
	//company
	
	
	public Employee() {}


	public Employee(String firstName, String lastName, Date dateOfBirth, String email, double salary, String address,
			String gender) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.salary = salary;
		this.address = address;
		this.gender = gender;
	}



	public Employee(int empId, String firstName, String lastName, Date dateOfBirth, String email, double salary,
			String address, String gender) {
		super();
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.salary = salary;
		this.address = address;
		this.gender = gender;
	}



	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth="
				+ dateOfBirth + ", email=" + email + ", salary=" + salary + ", address=" + address + ", gender="
				+ gender + "]";
	}


	public int getEmpId() {
		return empId;
	}


	public void setEmpId(int empId) {
		this.empId = empId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public double getSalary() {
		return salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	
	
	
}
