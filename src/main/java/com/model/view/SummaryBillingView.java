package com.model.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import com.model.finance.DataActivity;

public class SummaryBillingView {
	
	private DataActivity dataActivity;
	private BigDecimal sumBill;
	private LocalDate started_at_inactive;
	
	public SummaryBillingView(DataActivity dataActivity, BigDecimal sumBill, LocalDate started_at_inactive) {
		super();
		this.dataActivity = dataActivity;
		this.sumBill = sumBill;
		this.started_at_inactive = started_at_inactive;
	}

	public DataActivity getDataActivity() {
		return dataActivity;
	}

	public BigDecimal getSumBill() {
		return sumBill;
	}

	public LocalDate getStarted_at_inactive() {
		return started_at_inactive;
	}
}
