package com.capgemini.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.pojo.Login;

@Repository("employeeDao")
public interface EmployeeDao extends CrudRepository<Login, Integer> {

//	@Query("SELECT l FROM Login l WHERE l.username=:user AND l.password=:pass")
//	public Login isValidUser(Login login);
	
	public List<Login> findByUsernameAndPassword(String username, String password);
	
}
