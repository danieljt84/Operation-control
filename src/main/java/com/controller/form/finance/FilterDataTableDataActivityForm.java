package com.controller.form.finance;

import java.util.List;

import com.util.Status;

public class FilterDataTableDataActivityForm {

	private List<String> brand;
	private List<String> description;
	private List<String> shop;
	private List<Double> hoursContracted;
	private List<Double> daysInWeekContracted;
	private List<Status> status;
	private List<String> project;
	
	public List<String> getBrand() {
		return brand;
	}
	public void setBrand(List<String> brand) {
		this.brand = brand;
	}
	public List<String> getDescription() {
		return description;
	}
	public void setDescription(List<String> description) {
		this.description = description;
	}
	public List<String> getShop() {
		return shop;
	}
	public void setShop(List<String> shop) {
		this.shop = shop;
	}
	
	public List<Status> getStatus() {
		return status;
	}
	public void setStatus(List<Status> status) {
		this.status = status;
	}
	public List<Double> getHoursContracted() {
		return hoursContracted;
	}
	public void setHoursContracted(List<Double> hoursContracted) {
		this.hoursContracted = hoursContracted;
	}
	public List<Double> getDaysInWeekContracted() {
		return daysInWeekContracted;
	}
	public void setDaysInWeekContracted(List<Double> daysInWeekContracted) {
		this.daysInWeekContracted = daysInWeekContracted;
	}
	public List<String> getProject() {
		return project;
	}
	public void setProject(List<String> project) {
		this.project = project;
	}
}
