package com.capgemini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.capgemini.pojo.Login;

@Repository("employeeDao")
public class EmployeeDaoImpl implements IEmployeeDao {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public Login isValidUser(Login login) {
		Query query = em.createQuery("SELECT u FROM Login u WHERE u.username=:user AND u.password=:pass");
		
		query.setParameter("user", login.getUsername());
		query.setParameter("pass", login.getPassword());
		
		List<Login> userFound = query.getResultList();
		System.out.println(userFound.get(0));
		
		return (userFound.size() > 0) ? userFound.get(0) : null;
		
	}

}
