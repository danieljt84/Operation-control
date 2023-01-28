package com.controller.dto.finance;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;


public class BillingByBrandAndDataActivityDTO {
	
	@JsonProperty("dataActivity")
	private DataActivityDTO dataActivityDTO;
	private BigDecimal sumBilling;
	private LocalDate started_at_inactive;

	
	
	public BillingByBrandAndDataActivityDTO(DataActivityDTO dataActivityDTO, BigDecimal sumBilling,LocalDate started_at_inactive) {
		super();
		this.dataActivityDTO = dataActivityDTO;
		this.sumBilling = sumBilling;
		this.started_at_inactive = started_at_inactive;
	}
	public DataActivityDTO getDataActivityDTO() {
		return dataActivityDTO;
	}
	public void setDataActivityDTO(DataActivityDTO dataActivityDTO) {
		this.dataActivityDTO = dataActivityDTO;
	}
	public BigDecimal getSumBilling() {
		return sumBilling;
	}
	public void setSumBilling(BigDecimal sumBilling) {
		this.sumBilling = sumBilling;
	}
	public LocalDate getStarted_at_inactive() {
		return started_at_inactive;
	}
	public void setStarted_at_inactive(LocalDate started_at_inactive) {
		this.started_at_inactive = started_at_inactive;
	}

}
