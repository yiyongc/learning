package com.capgemini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.capgemini.pojo.Address;
import com.capgemini.pojo.Employee;

@Configuration
public class JavaConfig {
	
	@Bean(name = "myEmployee")
	public Employee getEmployee() {
		return new Employee(1, "Tom", "Jerry", 1200);
	}

	@Bean(name = "address")
	public Address getAddress() {
		return new Address("12", "Merchant Lane", "Singapore");
	}
	
	@Bean(name = "address2")
	public Address getAddress2() {
		return new Address("12", "m Lane", "Singapore");
	}
}
