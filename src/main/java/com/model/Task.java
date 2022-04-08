package com.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	@OneToOne(cascade = {CascadeType.ALL})
	private Shop shop;
	private String situation;
	private Date start;
	@Column(name = "_end")
	private Date end;
	private Date duration;
	private Integer activityTotal;
	private Integer activityDone;
	private Integer activityMissing;
	@OneToMany(cascade = {CascadeType.ALL})
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

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getDuration() {
		return duration;
	}

	public void setDuration(Date duration) {
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
