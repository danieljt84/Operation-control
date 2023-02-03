package com.controller.dto.finance;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;


public class BillingByBrandAndDataActivityDTO {
	
	@JsonProperty("dataActivity")
	private DataActivityDTO dataActivityDTO;
	private BigDecimal sumBilling;

	
	
	public BillingByBrandAndDataActivityDTO(DataActivityDTO dataActivityDTO, BigDecimal sumBilling) {
		super();
		this.dataActivityDTO = dataActivityDTO;
		this.sumBilling = sumBilling;
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
	

}
