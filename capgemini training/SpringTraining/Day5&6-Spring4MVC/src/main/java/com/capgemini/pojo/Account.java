package com.capgemini.pojo;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Account {
	
	private int accId;
	
	@NotEmpty(message="* Please Enter First Name")
	private String firstName;
	
	@NotEmpty(message="* Please Enter Last Name")
	private String lastName;
	
	@Past(message="* Date of Birth Must Be In The Past!")
	private Date dateOfBirth;
	
	private String address;
	private String occupation;
	
	@NotEmpty(message="* Please Select Gender")
	private String gender;
	
	@NotEmpty(message="* Please Enter Email")
	@Email(message="* Invalid Email Input")
	private String email;
	
	@NotEmpty(message="* Please Enter Mobile Number")
	@Length(min=8, max=8, message="* Mobile Number Must Be 8 Digits Long")
	private String mobile;
	
	public Account() {}


	public Account(String firstName, String lastName, Date dateOfBirth, String address, String occupation,
			String gender, String email, String mobile) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.occupation = occupation;
		this.gender = gender;
		this.email = email;
		this.mobile = mobile;
	}


	public Account(int accId, String firstName, String lastName, Date dateOfBirth, String address, String occupation,
			String gender, String email, String mobile) {
		this.accId = accId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.occupation = occupation;
		this.gender = gender;
		this.email = email;
		this.mobile = mobile;
	}


	@Override
	public String toString() {
		return "Account [accId=" + accId + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth="
				+ dateOfBirth + ", address=" + address + ", occupation=" + occupation + ", gender=" + gender
				+ ", email=" + email + ", mobile=" + mobile + "]";
	}


	public int getAccId() {
		return accId;
	}


	public void setAccId(int accId) {
		this.accId = accId;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
}
