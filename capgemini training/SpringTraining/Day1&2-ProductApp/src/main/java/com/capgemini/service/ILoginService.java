package com.capgemini.service;

import com.capgemini.model.Login;

public interface ILoginService {
	
	public boolean isValidLogin(Login login);
	
}
