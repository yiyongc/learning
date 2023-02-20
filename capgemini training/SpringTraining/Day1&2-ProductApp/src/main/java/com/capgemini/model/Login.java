package com.capgemini.model;

public class Login {
	
	private String userName;
	private String userPwd;
	
	public Login() {
		this.userName = null;
		this.userPwd = null;
	}
	
	public Login(String userName, String userPwd) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	@Override
	public String toString() {
		return "Login [userName=" + userName + ", userPwd=" + userPwd + "]";
	}
	
	
	
}
