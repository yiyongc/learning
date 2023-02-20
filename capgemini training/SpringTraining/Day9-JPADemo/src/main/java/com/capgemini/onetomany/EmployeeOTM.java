package com.capgemini.onetomany;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="employee_OTM")
@Entity
public class EmployeeOTM {

	@Id
	@GeneratedValue
	private int empId;
	private String firstName;
	private String lastName;
	
	@ManyToOne
	@JoinColumn(name="company_id_fk")
	private Company company;
	
	public EmployeeOTM() {}

	public EmployeeOTM(String firstName, String lastName, Company company) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.company = company;
	}

	@Override
	public String toString() {
		return "EmployeeOTM [empId=" + empId + ", firstName=" + firstName + ", lastName=" + lastName + "]";
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
}
