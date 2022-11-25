package com.model;
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
	@ManyToOne
	@JoinColumn(name="brand_id")
	private Brand brand;
	private Long idSystem;
	
	public Activity(Activity activity) {
		this.description = activity.getDescription();
		this.brand = activity.getBrand();
		this.type = activity.getType();
	}
	public Activity() {
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
	
	public void setDescription(String description) {
		this.description = description;
	} 
	public String getDescription() {
		return description;
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
