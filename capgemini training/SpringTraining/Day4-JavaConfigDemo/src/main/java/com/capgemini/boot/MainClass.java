package com.capgemini.boot;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.capgemini.config.JavaConfig;
import com.capgemini.pojo.Employee;

public class MainClass {
	
	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);

		//Employee employee = context.getBean(Employee.class);
		Employee employee2 = (Employee) context.getBean("myEmployee");
		employee2.setFirstName("Ben");
		System.out.println(employee2);
		
		context.registerShutdownHook();
		
		context.close();
	}
}
