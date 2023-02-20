package com.capgemini.boot;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.capgemini.manytomany.Delegate;
import com.capgemini.manytomany.Event;

public class MainForManyToMany {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpademo");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Event java = new Event(1001, "Java");
		Event oracle = new Event(1002, "Oracle");
		Event angular = new Event(1003, "Angular");
		
		Delegate del1 = new Delegate(101, "Jimmy");
		List<Event> jimmyList = new ArrayList<>();
		jimmyList.add(java);
		jimmyList.add(angular);
		del1.setEvents(jimmyList);
		
		Delegate del2 = new Delegate(102, "tom");
		List<Event> tomList = new ArrayList<>();
		tomList.add(java);
		tomList.add(oracle);
		del2.setEvents(tomList);
		
		Delegate del3 = new Delegate(103, "John");
		List<Event> johnList = new ArrayList<>();
		johnList.add(angular);
		johnList.add(oracle);
		del3.setEvents(johnList);
		
		et.begin();
//		em.persist(del1);
//		em.persist(del2);
//		em.persist(del3);
//		em.persist(java);
//		em.persist(oracle);
//		em.persist(angular);
		
		Event e = em.find(Event.class, 1001);
		System.out.println(e.getDelegates());
		
		Delegate d = em.find(Delegate.class, 101);
		System.out.println(d.getEvents());
		et.commit();

	}

}
