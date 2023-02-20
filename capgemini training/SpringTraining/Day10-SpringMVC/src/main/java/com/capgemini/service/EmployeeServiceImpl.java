package com.capgemini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.IEmployeeDao;
import com.capgemini.pojo.Login;

@Service("employeeService")
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	IEmployeeDao employeeDao;
	
	@Override
	@Transactional
	public Login isValidUser(Login login) {
		return employeeDao.isValidUser(login);
	}

}
