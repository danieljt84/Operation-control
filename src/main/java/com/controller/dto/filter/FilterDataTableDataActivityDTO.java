package com.controller.dto.filter;

import java.util.List;

public class FilterDataTableDataActivityDTO {
	
	private List<Object> brand;
	private List<Object> description;
	private List<Object> shop;
	private List<Object> hoursContracted;
	private List<Object> daysInWeekContracted;
	public List<Object> getBrand() {
		return brand;
	}
	public void setBrand(List<Object> brand) {
		this.brand = brand;
	}
	public List<Object> getDescription() {
		return description;
	}
	public void setDescription(List<Object> description) {
		this.description = description;
	}
	public List<Object> getShop() {
		return shop;
	}
	public void setShop(List<Object> shop) {
		this.shop = shop;
	}
	public List<Object> getHoursContracted() {
		return hoursContracted;
	}
	public void setHoursContracted(List<Object> hoursContracted) {
		this.hoursContracted = hoursContracted;
	}
	public List<Object> getDaysInWeekContracted() {
		return daysInWeekContracted;
	}
	public void setDaysInWeekContracted(List<Object> daysInWeekContracted) {
		this.daysInWeekContracted = daysInWeekContracted;
	}
}
