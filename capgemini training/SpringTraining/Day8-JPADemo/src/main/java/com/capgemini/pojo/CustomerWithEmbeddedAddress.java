package com.capgemini.pojo;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CustomerWithEmbeddedAddress {

	@Id
	@GeneratedValue
	private int custId;
	private String firstName;
	private String lastName;
	@Embedded
	private AddressEmbedded address;
	
	public CustomerWithEmbeddedAddress() {}
	
	public CustomerWithEmbeddedAddress(String firstName, String lastName, AddressEmbedded address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}

	public CustomerWithEmbeddedAddress(int custId, String firstName, String lastName, AddressEmbedded address) {
		this.custId = custId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}

	@Override
	public String toString() {
		return "Customer [custId=" + custId + ", firstName=" + firstName + ", lastName=" + lastName + ", address="
				+ address + "]";
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
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

	public AddressEmbedded getAddress() {
		return address;
	}

	public void setAddress(AddressEmbedded address) {
		this.address = address;
	}
	
	
	
}
