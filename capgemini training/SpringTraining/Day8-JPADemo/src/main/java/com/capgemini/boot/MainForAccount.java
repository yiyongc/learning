package com.capgemini.boot;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.capgemini.pojo.Account;
import com.capgemini.pojo.AccountId;

public class MainForAccount {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		Account acc = new Account(new AccountId(1001, 1001), "testAcc1", sdf.parse("07-09-2017"), 10000);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpademo");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		em.persist(acc);
		et.commit();
		
		
		
	}

}
