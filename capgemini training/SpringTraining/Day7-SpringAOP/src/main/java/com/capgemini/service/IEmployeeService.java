package com.capgemini.service;

import java.util.List;

import com.capgemini.pojo.Employee;

public interface IEmployeeService {

	public List<Employee> getAllEmployees();

	public Employee findEmployee(int id);
	
}
