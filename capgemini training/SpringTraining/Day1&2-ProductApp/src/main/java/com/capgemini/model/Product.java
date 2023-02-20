package com.capgemini.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Product {
	private int productId;
	private String productName; 
	private String productType;
	private Date productExpiry; 
	private String productDesc;
	private int productQty;
	private double productPrice;
	
	private Logger logger = Logger.getLogger("product");
	
	public Product(String productName, String productType, String productExpiry, String productDesc, String productQty,
			String productPrice) {
		super();
		this.productId = 0;
		this.productName = productName;
		this.productType = productType;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.productExpiry = sdf.parse(productExpiry);
		} catch (ParseException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
		this.productDesc = productDesc;
		this.productQty = Integer.parseInt(productQty);
		this.productPrice = Double.parseDouble(productPrice);
	}
	
	
	
	@Override
	public String toString() {
		return "Product [productName=" + productName + ", productType=" + productType + ", productExpiry="
				+ productExpiry + ", productDesc=" + productDesc + ", productQty=" + productQty + ", productPrice="
				+ productPrice + "]";
	}
	
	

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductType() {
		return productType;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public Date getProductExpiry() {
		return productExpiry;
	}
	
	public void setProductExpiry(Date productExpiry) {
		this.productExpiry = productExpiry;
	}
	
	public String getProductDesc() {
		return productDesc;
	}
	
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	
	public int getProductQty() {
		return productQty;
	}
	
	public void setProductQty(int productQty) {
		this.productQty = productQty;
	}
	
	public double getProductPrice() {
		return productPrice;
	}
	
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	
	
	
}
