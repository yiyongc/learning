package com.capgemini.boot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MainForEmployee {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpademo");
		EntityManager em = emf.createEntityManager();
		EntityTransaction entityTransaction = em.getTransaction();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.getClass();
//		
//		Employee employee = new Employee("Harry", "potter", 2000, sdf.parse("01-01-1999"), 
//				sdf.parse("03-07-2017"),"a@a.com", true, "capgemini");
//		Employee employee2 = new Employee("fruit", "cake", 5000, sdf.parse("15-09-1995"), 
//				sdf.parse("03-07-2017"),"b@b.com", false, "capgemini2");
//		Employee employee3 = new Employee("cupcake", "man", 2350, sdf.parse("17-08-1993"), 
//				sdf.parse("03-07-2017"),"c@c.com", true, "whatthehell");
//		Employee employee4 = new Employee("chronia", "zor", 7500, sdf.parse("20-04-1994"), 
//				sdf.parse("03-07-2017"),"d@d.com", true, "nubcake");
//		Employee employee5 = new Employee("mr", "wiggles", 5000, sdf.parse("12-02-1995"), 
//				sdf.parse("03-07-2017"),"e@e.com", true, "powderbath");
//		Employee employee6 = new Employee("Connor", "tax", 2300, sdf.parse("17-11-1990"), 
//				sdf.parse("03-07-2017"),"f@f.com", true, "youarenub");
//		Employee employee7 = new Employee("piggy", "seow", 14000, sdf.parse("01-07-1996"), 
//				sdf.parse("03-07-2017"),"g@g.com", true, "piggypork");
//		
//		entityTransaction.begin();
//		em.persist(employee);
//		em.persist(employee2);
//		em.persist(employee3);
//		em.persist(employee4);
//		em.persist(employee5);
//		em.persist(employee6);
//		em.persist(employee7);
//		entityTransaction.commit();
		
		entityTransaction.begin();
		
//		//Find one object using primary key
//		System.out.println(em.find(Employee.class, 13));
//		
//		//Update object
//		Employee emp = em.find(Employee.class, 12);
//		emp.setFirstName("Monster");
//		
//		//Delete
//		Employee emp2 = em.find(Employee.class, 12);
//		em.remove(emp2);
		
		//Select from employees
//		List<Employee> employees = em.createQuery("from Employee e where e.firstName = 'piggy'").getResultList();
//		for (Employee e : employees)
//			System.out.println(e);
//		
//		List<String> empfn = em.createQuery("select firstName from Employee").getResultList();
//		for (String e : empfn)
//			System.out.println(e);
//		
//		List<Employee> employeeList = em.createQuery("select new Employee(firstName,lastName,salary) from Employee").getResultList();
//		for (Employee e : employeeList)
//			System.out.println(e);
		
//		List<Object> empMap = em.createQuery("select new Map(empId,firstName) from Employee").getResultList();
//		for (Object e : empMap)
//			System.out.println(e);
		
		List<Object> empMap2 = em.createQuery("select new Map(empId, new Employee(firstName, lastName, salary)) from Employee").getResultList();
		for (Object e : empMap2)
			System.out.println(e);
		
		entityTransaction.commit();
		
		
		
	}
	
}
