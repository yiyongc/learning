package com.capgemini.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="loginusers")
public class Login {

	@Id
	@GeneratedValue
	@Column(name="idusers")
	private int userId;
	@NotEmpty(message="* Please enter a username!")
	private String username;
	@NotEmpty(message="* Please enter a password!")
	private String password;
	private String userType;
	
	public Login() {}

	public Login(String username, String password, String userType) {
		this.username = username;
		this.password = password;
		this.userType = userType;
	}

	public Login(int userId, String username, String password, String userType) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "Login [userId=" + userId + ", username=" + username + ", password=" + password + ", userType="
				+ userType + "]";
	}


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
}
