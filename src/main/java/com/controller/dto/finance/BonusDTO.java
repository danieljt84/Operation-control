package com.controller.dto.finance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.controller.form.DataActivityOnlyIdForm;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BonusDTO {

	private Long id;
	private String responsible;
	private LocalDate createdAt;
	private LocalDate finishedAt;
	private Integer daysInWeek;
	private BigDecimal price;
	private List<DataActivityDTO> datasActivity;
	
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
	public List<DataActivityDTO> getDatasActivity() {
		return datasActivity;
	}
	public void setDatasActivity(List<DataActivityDTO> datasActivity) {
		this.datasActivity = datasActivity;
	}
}
