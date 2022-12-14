package com.model.finance;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.model.Activity;
import com.model.Shop;
import com.util.Status;

@Entity
@Table(schema = "finance")
public class DataActivity {
	
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Activity activity;
	@ManyToOne
	private Shop shop;
	private BigDecimal price;
	private Double hoursContracted;
	private Long daysInWeekContracted;
	private String type;
	private LocalDate createdAt;
	private LocalDate finishedAt;
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDate getFinishedAt() {
		return finishedAt;
	}
	public void setFinishedAt(LocalDate finishedAt) {
		this.finishedAt = finishedAt;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
