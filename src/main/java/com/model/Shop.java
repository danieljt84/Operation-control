package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Shop {
	
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String name;
	@Column(unique = true)
	private Long idSystem;
	
	public Shop() {
		super();
	}
	public Shop(String name) {
		this.name = name;
	}
	
	public Long getIdSystem() {
		return idSystem;
	}
	public void setIdSystem(Long idSystem) {
		this.idSystem = idSystem;
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
	}
}
