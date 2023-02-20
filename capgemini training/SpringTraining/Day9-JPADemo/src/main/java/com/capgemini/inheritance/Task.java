package com.capgemini.inheritance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;


@Entity
public class Task extends Module {

	@ElementCollection
	private List<String> taskNames = new ArrayList<>();
	
	public Task() {}
	
	public Task(List<String> taskNames) {
		this.taskNames = taskNames;
	}

	@Override
	public String toString() {
		return "Task [taskName=" + taskNames + "]";
	}

	public List<String> getTaskNames() {
		return taskNames;
	}

	public void setTaskNames(List<String> taskNames) {
		this.taskNames = taskNames;
	}
	
	
	
}
