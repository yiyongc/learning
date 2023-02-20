package com.capgemini.boot;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.capgemini.onetoone.Address;
import com.capgemini.onetoone.Customer;

public class MainForCustomer {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpademo");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Address add1 = new Address(1001, "6", "battery road", "Singapore");
		Address add2 = new Address(1002, "12", "merchant road", "Singapore");
		Customer cust1 = new Customer("tom", "jerry", add1);
		Customer cust2 = new Customer("tim", "tam", add2);
		
		et.begin();
		em.persist(cust1);
		em.persist(cust2);
		em.persist(add1);
		em.persist(add2);
		et.commit();
		
		
	}
	
	
}

