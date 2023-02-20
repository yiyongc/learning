package com.capgemini.manytomany;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {
	
	@Id
	private int eventId;
	private String eventName;
	@ManyToMany
	@JoinTable(name="event_delegate", joinColumns= {@JoinColumn(name="eventId")},inverseJoinColumns = {@JoinColumn(name="delegateId")})
	private List<Delegate> delegates = new ArrayList<>();
	
	public Event() {}

	public Event(int eventId, String eventName) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
	}
	
	public Event(int eventId, String eventName, List<Delegate> delegates) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.delegates = delegates;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", eventName=" + eventName + "]";
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public List<Delegate> getDelegates() {
		return delegates;
	}

	public void setDelegates(List<Delegate> delegates) {
		this.delegates = delegates;
	}
	
	
}
