package com.capgemini.pojo;

public class Address {
	
	private String doorNo;
	private String streetName;
	private String city;
	
	//no-argument constructor
	public Address() {}
	
	public Address(String doorNo, String streetName, String city) {
		this.doorNo = doorNo;
		this.streetName = streetName;
		this.city = city;
	}
	
	@Override
	public String toString() {
		return "Address [doorNo=" + doorNo + ", streetName=" + streetName + ", city=" + city + "]";
	}

	public String getDoorNo() {
		return doorNo;
	}
	
	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
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
}
