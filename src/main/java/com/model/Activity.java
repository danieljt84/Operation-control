package com.model;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Activity {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String type;
	private String description;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="brand_id")
	private Brand brand;
	private String situation;
	private LocalDateTime start;
	@Column(name = "_end")
	private LocalDateTime end;
	
	public Activity(Activity activity) {
		this.description = activity.getDescription();
		this.situation = activity.getSituation();
		this.start = activity.getStart();
		this.end = activity.getEnd();
		this.brand = activity.getBrand();
		this.type = activity.getType();
	}
	public Activity() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSituation() {
		return situation;
	}
	public void setDescription(String description) {
		if(description.contains("Diária")) this.type = "diaria";
		if(description.contains("Pesquisa Semana")) this.type = "semanal";
		this.description = description;
	} 
	public String getDescription() {
		return description;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
}
