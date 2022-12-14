package com.controller.dto.finance;

import com.controller.dto.operation.BrandDTO;

public class ActivityDTO {
	
	private Long id;
	private String description;
	private BrandDTO brand;
	
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
	public BrandDTO getBrand() {
		return brand;
	}
	public void setBrand(BrandDTO brand) {
		this.brand = brand;
	}
}
