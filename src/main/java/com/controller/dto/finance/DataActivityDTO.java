package com.controller.dto.finance;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.controller.dto.ShopDTO;

public class DataActivityDTO {

	private Long id;
	private ActivityDTO activity;
	private ShopDTO shop;
	private BigDecimal price;
	private Double hoursContracted;
	private Long daysInWeekContracted;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public ActivityDTO getActivity() {
		return activity;
	}
	public void setActivity(ActivityDTO activity) {
		this.activity = activity;
	}
	public ShopDTO getShop() {
		return shop;
	}
	public void setShop(ShopDTO shop) {
		this.shop = shop;
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
