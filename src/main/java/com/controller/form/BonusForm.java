package com.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class BonusForm {
	
	private Long id;
	@NotEmpty
	private String responsible;
	@NotEmpty
	private LocalDate createdAt;
	@NotEmpty
	private LocalDate finishedAt;
	@NotEmpty
	private List<DataActivityOnlyIdForm> datasActivity;
	private Integer daysInWeek;
	private BigDecimal price;
	
	public BonusForm() {
		teste();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResponsible() {
		return responsible;
	}
	public void setResponsible(String responsible) {
		this.responsible = responsible;
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
	public List<DataActivityOnlyIdForm> getDatasActivity() {
		return datasActivity;
	}
	public void setDatasActivity(List<DataActivityOnlyIdForm> datasActivity) {
		this.datasActivity = datasActivity;
	}
	public Integer getDaysInWeek() {
		return daysInWeek;
	}
	public void setDaysInWeek(Integer daysInWeek) {
		this.daysInWeek = daysInWeek;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	private void teste() {
		System.out.println("oi");
	}
	
}
