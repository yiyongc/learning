package com.capgemini.boot;

import java.text.ParseException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.capgemini.config.JavaSpringConfig;
import com.capgemini.service.EmployeeService;

public class MainForEmployee {


	public static void main(String[] args) throws ParseException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaSpringConfig.class);
		//AbstractApplicationContext context = new ClassPathXmlApplicationContext("myBeans.xml");
		
		EmployeeService service =  context.getBean(EmployeeService.class);
		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//		sdf.getClass();
//		Employee employee1 = new Employee("Tan", "Tan", sdf.parse("03-03-1993"), "tantan@jerry.com", 300.0, "Tantan street", "female");
//		Employee employee2 = new Employee("Emp", "Man", sdf.parse("04-04-1994"), "empman@jerry.com", 500.0, "empman lane", "male");
//		Employee employee3 = new Employee("Ip", "Man", sdf.parse("06-06-1996"), "ipman@jerry.com", 600.0, "ipman alley", "female");
//		List<Employee> emps = new ArrayList<>();
//		emps.add(employee3);
//		emps.add(employee2);
//		emps.add(employee1);
////		
//		service.saveAll(emps);
		
//		
//		for(Employee e : service.findAll())
//			System.out.println(e);
//		
//		System.out.println(service.findOne(3));
//		
//		service.delete(4);
//		Employee employee1 = new Employee("Tom", "Tan", sdf.parse("05-05-1995"), "tom@jerry.com", 300.0, "Cartoon Network street", "male");
//		employee1.setEmpId(1);
//		
//		service.save(employee1);
		
		
//		for(Employee e : service.findByFirstName("Tom"))
//			System.out.println(e);
		
//		for(Employee e : service.findByFirstNameAndLastName("Tom", "Tom"))
//			System.out.println(e);
		
//		for(Employee e : service.findByFirstNameIgnoreCase("tom"))
//			System.out.println(e);
		
//		for(Employee e : service.findByFirstNameOrSalary("Tom", 600))
//			System.out.println(e);
//		System.out.println("HEREEEEEE");
//		System.out.println(service.getByEmpId(2));
//		
//		for(Employee e : service.readByGender("male"))
//			System.out.println(e);
		
		System.out.println(service.countByGender("male"));
//		
//		for(Employee e : service.findDistinctByGender("female"))
//			System.out.println(e);
	
//		for(Employee e : service.findByFirstNameContainsIgnoreCase("_a%"))
//			System.out.println(e);
		
//		for(Employee e : service.searchForEmployee(300))
//			System.out.println(e);
		
		context.close();

	}

}
