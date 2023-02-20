package com.capgemini.inheritance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Module extends Project {
	
	@ElementCollection
	private List<String> moduleNames = new ArrayList<>();
	
	public Module() {}
	
	public Module(List<String> moduleNames) {
		this.moduleNames = moduleNames;
	}

	@Override
	public String toString() {
		return "Module [moduleNames=" + moduleNames + "]";
	}

	public List<String> getModuleNames() {
		return moduleNames;
	}

	public void setModuleNames(List<String> moduleNames) {
		this.moduleNames = moduleNames;
	}
	
	
}
