package com.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Task_Activity {
	
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "activity_id")
	private Activity activity;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "task_id")
	private Task task;
	private String type;
	private String situation;
	private LocalDateTime start;
	@Column(name = "_end")
	private LocalDateTime end;
	private LocalTime duration;
	
	public Task_Activity(Task_Activity task_Activity) {
		super();
		this.id = task_Activity.getId();
		this.activity = task_Activity.getActivity();
		this.task = task_Activity.getTask();
		this.type = task_Activity.getType();
		this.situation = task_Activity.getSituation();
		this.start = task_Activity.getStart();
		this.end = task_Activity.getEnd();
		this.duration = task_Activity.getDuration();
	}
	
	public Task_Activity() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public LocalTime getDuration() {
		return duration;
	}
	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}
}
