package com.capgemini.pojo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionDemo {

	private List<String> names;
	private Set<String> nickNames;
	private Map<Address, Employee> petNames;
	
	//no-argument constructor
	public CollectionDemo() {}



	@Override
	public String toString() {
		return "CollectionDemo [names=" + names + ", nickNames=" + nickNames + ", petNames=" + petNames + "]";
	}



	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public Set<String> getNickNames() {
		return nickNames;
	}

	public void setNickNames(Set<String> nickNames) {
		this.nickNames = nickNames;
	}

	public Map<Address, Employee> getPetNames() {
		return petNames;
	}

	public void setPetNames(Map<Address, Employee> petNames) {
		this.petNames = petNames;
	}

	
}
