package com.model.view;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "`billing_by_brand`")

public class BillingByBrandView {

	@Id
	private Long id;
	private Long brandId;
	private Long sumBilling;
	
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public Long getSumBilling() {
		return sumBilling;
	}
	public void setSumBilling(Long sumBilling) {
		this.sumBilling = sumBilling;
	}
	
	
}
