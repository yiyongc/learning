package com.capgemini.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.capgemini.pojo.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl implements IEmployeeDao {
	
	static List<Employee> employees = new ArrayList<>();
	
	//Just for testing purposes
	static {
		Employee emp1 = new Employee(1, "Tom", "Jerry", 2300);
		Employee emp2 = new Employee(2, "Jack", "Lito", 1000);
		Employee emp3 = new Employee(3, "Ben", "Button", 3000);
		Employee emp4 = new Employee(4, "Nick", "Cage", 5400);
		Employee emp5 = new Employee(5, "Harry", "Potter", 1200);
		
		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);
		employees.add(emp4);
		employees.add(emp5);
	}
	
	public List<Employee> getAllEmployees() {
		return employees;
	}

	@Override
	public Employee findEmployee(int id) {
		for (Employee emp : employees) {
			if (emp.getEmpId() == id)
				return emp;
		}
		
		return null;
	}

}
