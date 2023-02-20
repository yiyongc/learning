package com.capgemini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.IEmployeeDao;
import com.capgemini.pojo.Employee;

@Service("employeeService")
public class EmployeeServiceImpl implements IEmployeeService {
	
	@Autowired
	private IEmployeeDao employeeDao;
	
	@Override
	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}
	
	@Override
	public Employee findEmployee(int id) {
		Employee emp = null;
		emp = employeeDao.findEmployee(id);
		
		if(emp == null) 
			throw new NullPointerException("Employee not found");
		
		return emp;
	}

}
