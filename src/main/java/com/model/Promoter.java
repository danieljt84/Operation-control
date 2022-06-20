package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.util.Status;

@Entity
public class Promoter {
	@Id@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String cpf;
	private String grade;
	@ManyToOne
	private Team team;
	private Double mediaPassagem;
	private String enterprise;
	private Status status;
	
	public Promoter() {
		super();
	}
	
	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	public Promoter(String name) {
		this.name = name;
		this.status = Status.ATIVO;
		this.setGrade();
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public Team getTeam() {
		return team;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		setGrade();
	}
	private void setGrade() {
		if(this.name.contains("GRADE 1")) this.grade = "GRADE 1"; 
		if(this.name.contains("GRADE 2")) this.grade = "GRADE 2"; 
		if(this.name.contains("GRADE 3")) this.grade = "GRADE 3"; 
		if(this.name.contains("GRADE 4")) this.grade = "GRADE 4";
	}
	public String getGrade() {
		return grade;
	}

	public Double getMediaPassagem() {
		return mediaPassagem;
	}

	public void setMediaPassagem(Double mediaPassagem) {
		this.mediaPassagem = mediaPassagem;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
}
