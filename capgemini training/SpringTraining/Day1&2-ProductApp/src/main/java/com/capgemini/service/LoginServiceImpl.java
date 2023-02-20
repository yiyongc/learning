package com.capgemini.service;

import com.capgemini.dao.ILoginDao;
import com.capgemini.dao.LoginDaoImpl;
import com.capgemini.model.Login;

public class LoginServiceImpl implements ILoginService {
	
	private ILoginDao loginDao = new LoginDaoImpl();
	
	@Override
	public boolean isValidLogin(Login login) {
		return loginDao.isValidUser(login);
	}

}
