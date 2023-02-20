package com.capgemini.boot;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.capgemini.pojo.Employee;

public class MainClass {
	
	public static void main(String[] args) {
		
//		ApplicationContext context = new ClassPathXmlApplicationContext("myBeans.xml");
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("myBeans.xml");

		Employee employee = (Employee) context.getBean("employee");

		System.out.println(employee);
		
		context.registerShutdownHook();
		
		context.close();
	}
}
