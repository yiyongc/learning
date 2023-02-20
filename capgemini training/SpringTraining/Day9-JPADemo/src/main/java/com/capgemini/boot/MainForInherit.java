package com.capgemini.boot;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.capgemini.inheritance.Task;

public class MainForInherit {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpademo");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
//		Module module = new Module();
//		module.setProjectName("Fund Transfer to Other Banks");
//		module.setProjectId(1001);
//		module.getModuleNames().add("NEFT FundTransfer");
//		module.getModuleNames().add("MODULE 2");
//		
//		Module module2 = new Module();
//		module2.setProjectName("PROJECT 2");
//		module2.setProjectId(1002);
//		module2.getModuleNames().add("MODULE 3");
//		module.getModuleNames().add("MODULE 4");
		
//		Task task = new Task();
//		task.setProjectId(1001);
//		task.setProjectName("Fund Transfer to Other Banks");
//		task.getModuleNames().add("MODULE 1");
//		task.getTaskNames().add("TASK 1");
//		task.getTaskNames().add("Task 2");
//		
//		Task task0 = new Task();
//		task0.setProjectId(1001);
//		task0.setProjectName("Fund Transfer to Other Banks");
//		task0.getModuleNames().add("MODULE 2");
//		task0.getTaskNames().add("TASK 3");
//		task0.getTaskNames().add("Task 4");
		
		Task task1 = new Task();
		task1.setProjectId(1002);
		task1.setProjectName("PROJECT 2");
		task1.getModuleNames().add("MODULE 3");
		task1.getModuleNames().add("MODULE 4");
		task1.getTaskNames().add("TASK 5");
		task1.getTaskNames().add("TASK 6");
		
//		Task task2 = new Task();
//		task2.setProjectId(1002);
//		task2.setProjectName("PROJECT 2");
//		task2.getModuleNames().add("MODULE 4");
//		task2.getTaskNames().add("TASK 7");
//		task2.getTaskNames().add("TASK 8");
//		
		et.begin();
		//em.persist(task0);
		em.persist(task1);
		//em.persist(task2);
		//em.persist(task);
//		em.persist(module);
//		em.persist(module2);
		et.commit();

	}

}

