package com.capgemini.onetomany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Company {

	@Id
	private int companyId;
	private String companyName;
	
	@OneToMany(targetEntity=EmployeeOTM.class, mappedBy="company", fetch=FetchType.EAGER)
	private List<EmployeeOTM> employees = new ArrayList<>();
	
	public Company() {}

	public Company(int companyId, String companyName) {
		this.companyId = companyId;
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", companyName=" + companyName + ", employees=" + employees + "]";
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<EmployeeOTM> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeOTM> employees) {
		this.employees = employees;
	}
	
	
}
