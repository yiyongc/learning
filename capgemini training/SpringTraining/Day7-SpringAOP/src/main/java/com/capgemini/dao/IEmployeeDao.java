package com.capgemini.dao;

import java.util.List;

import com.capgemini.pojo.Employee;

public interface IEmployeeDao {
	
	public List<Employee> getAllEmployees();
	
	public Employee findEmployee(int id);
}
