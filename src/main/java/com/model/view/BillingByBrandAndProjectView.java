package com.model.view;

public class BillingByBrandAndProjectView {
	
	private Long id;
	private Long brandId;
	private String Project;
	private Double sumBilling;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getProject() {
		return Project;
	}
	public void setProject(String project) {
		Project = project;
	}
	public Double getSumBilling() {
		return sumBilling;
	}
	public void setSumBilling(Double sumBilling) {
		this.sumBilling = sumBilling;
	}
}
