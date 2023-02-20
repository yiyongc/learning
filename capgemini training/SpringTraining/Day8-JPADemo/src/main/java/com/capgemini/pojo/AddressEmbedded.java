package com.capgemini.pojo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class AddressEmbedded implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String unitNo;
	private String streetName;
	private String city;
	
	public AddressEmbedded() {}

	public AddressEmbedded(String unitNo, String streetName, String city) {
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
	
	
}
