package com.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

import com.util.DurationConverter;

@Entity
public class DataTask{
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	@ManyToOne(cascade ={CascadeType.MERGE})
	@JoinColumn(name="promoter_id", referencedColumnName = "id")
	private Promoter promoter;
	@ManyToOne
	private Team team;
	private String situation;
	private int taskTotal;
	private int taskCanceled;
	private int taskDone;
	private int taskDoing;
	@OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
	private List<Task> tasks;
	private String project;	
	@Convert(converter = DurationConverter.class)
	private Duration duration;
	
	public DataTask(DataTask data) {
		this.date = data.getDate();
		this.situation = data.getSituation();
		this.taskCanceled = data.getTaskCanceled();
		this.taskDone = data.getTaskDone();
		this.taskTotal = data.getTaskTotal();
		this.tasks = data.getTasks();
		this.team = data.getTeam();
		this.project = data.getProject();
		this.promoter = data.getPromoter();
	}
	
	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public DataTask() {}
	
	public int getTaskDoing() {
		return taskDoing;
	}

	public void setTaskDoing(int taskDoing) {
		this.taskDoing = taskDoing;
	}

	public Promoter getPromoter() {
		return promoter;
	}
	public void setPromoter(Promoter promoter) {
		this.promoter = promoter;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
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
