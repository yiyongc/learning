package com.capgemini.boot;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.capgemini.config.JavaSpringConfig;
import com.capgemini.pojo.Employee;
import com.capgemini.service.IEmployeeService;

public class MainClass {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaSpringConfig.class);
		
		IEmployeeService employeeService = (IEmployeeService) context.getBean("employeeService");
		
		List<Employee> employees = employeeService.getAllEmployees();
		
		for (Employee emp : employees) {
			System.out.println(emp);
		}
		
		
		System.out.println(employeeService.findEmployee(3));
		
		//Might or might not need
		context.close();
	}

}
