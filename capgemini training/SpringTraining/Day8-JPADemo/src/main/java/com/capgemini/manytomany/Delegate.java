package com.capgemini.manytomany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Delegate {
	
	@Id
	private int delegateId;
	private String delegateName;
	@ManyToMany
	@JoinTable(name="event_delegate", joinColumns= {@JoinColumn(name="delegateId")},inverseJoinColumns = {@JoinColumn(name="eventId")})
	private List<Event> events = new ArrayList<>();
	
	public Delegate() {}

	public Delegate(int delegateId, String delegateName) {
		super();
		this.delegateId = delegateId;
		this.delegateName = delegateName;
	}

	public Delegate(int delegateId, String delegateName, List<Event> events) {
		super();
		this.delegateId = delegateId;
		this.delegateName = delegateName;
		this.events = events;
	}

	@Override
	public String toString() {
		return "Delegate [delegateId=" + delegateId + ", delegateName=" + delegateName + "]";
	}

	public int getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(int delegateId) {
		this.delegateId = delegateId;
	}

	public String getDelegateName() {
		return delegateName;
	}

	public void setDelegateName(String delegateName) {
		this.delegateName = delegateName;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	
}
