package com.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class Task {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	private Shop shop;
	private String situation;
	private LocalTime start;
	@Column(name = "_end")
	private LocalTime end;
	private LocalTime duration;
	private Integer activityTotal;
	private Integer activityDone;
	private Integer activityMissing;
	@OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
	private List<Activity> activities;
	
	public Task() {
		// TODO Auto-generated constructor stub
	}
	public Task(Task task) {
		super();
		this.shop = task.getShop();
		this.situation = task.getSituation();
		this.start = task.getStart();
		this.end = task.getEnd();
		this.duration = task.getDuration();
		this.activityTotal = task.getActivityTotal();
		this.activityDone = task.getActivityDone();
		this.activityMissing = task.getActivityMissing();
		this.activities = task.getActivities();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}

	public LocalTime getDuration() {
		return duration;
	}

	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}

	public Integer getActivityTotal() {
		return activityTotal;
	}

	public void setActivityTotal(Integer activityTotal) {
		this.activityTotal = activityTotal;
	}

	public Integer getActivityDone() {
		return activityDone;
	}

	public void setActivityDone(Integer activityDone) {
		this.activityDone = activityDone;
	}

	public Integer getActivityMissing() {
		return activityMissing;
	}

	public void setActivityMissing(Integer activityMissing) {
		this.activityMissing = activityMissing;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
}
