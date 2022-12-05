package com.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class DataActivityForm {
	
	private Long id;
	@NotNull
	private Long activityId;
	@NotNull
	private Long shopId;
	@NotNull@Digits(fraction = 2,integer = 100000)
	private BigDecimal price;
	@NotNull @Digits(fraction = 1,integer=10)
	private Double hoursContracted;
	@NotNull @Digits(fraction = 0,integer=10)
	private Long daysInWeekContracted;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Double getHoursContracted() {
		return hoursContracted;
	}
	public void setHoursContracted(Double hoursContracted) {
		this.hoursContracted = hoursContracted;
	}
	public Long getDaysInWeekContracted() {
		return daysInWeekContracted;
	}
	public void setDaysInWeekContracted(Long daysInWeekContracted) {
		this.daysInWeekContracted = daysInWeekContracted;
	}
}
