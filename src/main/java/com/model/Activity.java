package com.model;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Activity {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private String situation;
	private LocalDateTime start;
	@Column(name = "_end")
	private LocalDateTime end;
	
	public Activity(Activity activity) {
		this.description = activity.getDescription();
		this.situation = activity.getSituation();
		this.start = activity.getStart();
		this.end = activity.getEnd();
	}
	public Activity() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public LocalDateTime getEnd() {
		return end;
	}
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
}
