package com.capgemini.boot;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.capgemini.onetomany.Company;

public class MainForOneToMany {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpademo");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
//		Company company = new Company(1001, "Capgemini Singapore");
//		Company company2 = new Company(2001, "Google Inc");
		
//		EmployeeOTM emp1 = new EmployeeOTM("tom", "jerry", company);
//		EmployeeOTM emp2 = new EmployeeOTM("Tim", "Tam", company);
//		EmployeeOTM emp3 = new EmployeeOTM("Harry", "Jack", company2);
//		EmployeeOTM emp4 = new EmployeeOTM("Mackell", "Crack", company2);
		
		et.begin();
//		em.persist(company);
//		em.persist(company2);
//		em.persist(emp1);
//		em.persist(emp2);
//		em.persist(emp3);
//		em.persist(emp4);
		
		Company c = em.find(Company.class, 1001);
		
		
		et.commit();
		System.out.println(c.getEmployees());

	}

}
