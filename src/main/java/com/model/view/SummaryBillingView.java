package com.model.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.model.finance.DataActivity;
import com.model.finance.InactiveControl;

public class SummaryBillingView {
	
	private DataActivity dataActivity;
	private BigDecimal sumBill;
	private String hasInactive;
	private String hasBonus;
	private LocalDate lastStartedAt;
	private LocalDate lastCreatedAt;
	private List<InactiveControl> inactiveControls;
	
	public SummaryBillingView(DataActivity dataActivity, BigDecimal sumBill, String hasInactive, String hasBonus) {
		super();
		this.dataActivity = dataActivity;
		this.sumBill = sumBill;
		this.hasInactive = hasInactive;
		this.hasBonus = hasBonus;
	}

	public DataActivity getDataActivity() {
		return dataActivity;
	}

	public BigDecimal getSumBill() {
		return sumBill;
	}

	public String getHasInactive() {
		return hasInactive;
	}

	public void setHasInactive(String hasInactive) {
		this.hasInactive = hasInactive;
	}

	public String getHasBonus() {
		return hasBonus;
	}

	public void setHasBonus(String hasBonus) {
		this.hasBonus = hasBonus;
	}

	public void setDataActivity(DataActivity dataActivity) {
		this.dataActivity = dataActivity;
	}

	public void setSumBill(BigDecimal sumBill) {
		this.sumBill = sumBill;
	}

	public List<InactiveControl> getInactiveControls() {
		return inactiveControls;
	}

	public void setInactiveControls(List<InactiveControl> inactiveControls) {
		this.inactiveControls = inactiveControls;
	}

	public LocalDate getLastStartedAt() {
		return lastStartedAt;
	}

	public void setLastStartedAt(LocalDate lastStartedAt) {
		this.lastStartedAt = lastStartedAt;
	}

	public LocalDate getLastCreatedAt() {
		return lastCreatedAt;
	}

	public void setLastCreatedAt(LocalDate lastCreatedAt) {
		this.lastCreatedAt = lastCreatedAt;
	}
	
	

	
}
