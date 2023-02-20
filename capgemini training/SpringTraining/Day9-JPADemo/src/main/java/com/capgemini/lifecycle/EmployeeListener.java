package com.capgemini.lifecycle;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class EmployeeListener {

	@PrePersist
	public void pre_persist(Employee e) {
		System.out.println("PRE PERSIST YO" + e.getFirstName());
	}
	
	@PostPersist
	public void post_persist(Employee e) {
		System.out.println("POST PERSIST ALREADY" + e.getFirstName());
	}
	
	@PreUpdate
	public void pre_update(Employee e) {
		System.out.println("TRYING TO UPDATE" + e.getFirstName());
	}
	
	@PostUpdate
	public void post_update(Employee e) {
		System.out.println("UPDATED............." + e.getFirstName());
	}
	
	@PreRemove
	public void pre_remove(Employee e) {
		System.out.println("TRYING TO REMOVE....." + e.getFirstName());
	}
	
	@PostRemove
	public void post_remove(Employee e) {
		System.out.println("REMOVED WOOHOOOOOOOOO" + e.getFirstName());
	}
}
