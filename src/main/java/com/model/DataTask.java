package com.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class DataTask{
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	@ManyToOne(cascade ={CascadeType.ALL})
	@JoinColumn(name="promoter_id", referencedColumnName = "id")
	private Promoter promoter;
	private String team;
	private String situation;
	private int taskTotal;
	private int taskCanceled;
	private int taskDone;
	private Date duration;
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Task> tasks;
	private String project;
	
	public DataTask(DataTask data) {
		this.date = data.getDate();
		this.duration = data.getDuration();
		this.situation = data.getSituation();
		this.taskCanceled = data.getTaskCanceled();
		this.taskDone = data.getTaskDone();
		this.taskTotal = data.getTaskTotal();
		this.tasks = data.getTasks();
		this.team = data.getTeam();
		this.project = data.getProject();
		this.promoter = data.getPromoter();
	}
	
	public DataTask() {
		
	}
	
	public Promoter getPromoter() {
		return promoter;
	}
	public void setPromoter(Promoter promoter) {
		this.promoter = promoter;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public int getTaskTotal() {
		return taskTotal;
	}
	public void setTaskTotal(int taskTotal) {
		this.taskTotal = taskTotal;
	}
	public int getTaskCanceled() {
		return taskCanceled;
	}
	public void setTaskCanceled(int taskCanceled) {
		this.taskCanceled = taskCanceled;
	}
	public int getTaskDone() {
		return taskDone;
	}
	public void setTaskDone(int taskDone) {
		this.taskDone = taskDone;
	}
	public Date getDuration() {
		return duration;
	}
	public void setDuration(Date duration) {
		this.duration = duration;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		switch (project) {
		case "4pmktcfixo":
			this.project = Project.FIXO_RJ.toString();
			break;
		case "4pmktcompartilhado":
			this.project = Project.COMPARTILHADO_RJ.toString();
			break;
		case "4pmktcompartilhadoes":
			this.project = Project.COMPARTILHADO_ES.toString();
			break;
		case "4pmktcompartilhadosp":
			this.project = Project.COMPARTILHADO_SP.toString();
			break;
		case "4pmktcompartilhadoba":
			this.project = Project.COMPARTILHADO_BA.toString();
			break;
		default:
			break;
		}
	}
}
