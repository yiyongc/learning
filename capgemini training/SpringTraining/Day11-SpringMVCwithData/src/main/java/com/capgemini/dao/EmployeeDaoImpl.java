package com.capgemini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.capgemini.pojo.Login;


public class EmployeeDaoImpl {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")

	public Login isValidUser(Login login) {
		Query query = em.createQuery("SELECT l FROM Login l WHERE l.username=:user AND l.password=:pass");
		
		query.setParameter("user", login.getUsername());
		query.setParameter("pass", login.getPassword());
		
		List<Login> userFound = query.getResultList();
		System.out.println(userFound.get(0));
		
		return (userFound.size() > 0) ? userFound.get(0) : null;
		
	}

}
