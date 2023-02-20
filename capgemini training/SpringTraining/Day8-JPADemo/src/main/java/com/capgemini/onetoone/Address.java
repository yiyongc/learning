package com.capgemini.onetoone;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Address{

	@Id
	private int addressId;
	private String unitNo;
	private String streetName;
	private String city;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="customer_id_fk")
	private Customer customer;
	
	public Address() {}

	public Address(int id, String unitNo, String streetName, String city) {
		this.addressId = id;
		this.unitNo = unitNo;
		this.streetName = streetName;
		this.setCity(city);
	}

	@Override
	public String toString() {
		return "Address [unitNo=" + unitNo + ", streetName=" + streetName + "]";
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
