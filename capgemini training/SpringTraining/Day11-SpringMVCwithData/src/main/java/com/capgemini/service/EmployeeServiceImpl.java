package com.capgemini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.pojo.Login;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;
	
	@Override
	@Transactional
	public Login isValidUser(Login login) {
		List<Login> logins = employeeDao.findByUsernameAndPassword(login.getUsername(), login.getPassword());
		if(!logins.isEmpty())
			return logins.get(0);
		else
			return null;
	}

}
