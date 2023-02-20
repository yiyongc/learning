package com.capgemini.dao;

import com.capgemini.model.Login;

public interface ILoginDao {
	
	public boolean isValidUser(Login login);
}
