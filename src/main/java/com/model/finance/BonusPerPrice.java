package com.model.finance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.model.Activity;
import com.util.finance.BonusPerValueType;

@DiscriminatorValue(value = "PER_PRICE")
@Entity
public class BonusPerPrice extends Bonus {
	@ManyToOne
	private DataActivity dataActivity;
	private BigDecimal price;
	private LocalDate start;
	private LocalDate end;
	
	public LocalDate getStart() {
		return start;
	}
	public void setStart(LocalDate start) {
		this.start = start;
	}
	public LocalDate getEnd() {
		return end;
	}
	public void setEnd(LocalDate end) {
		this.end = end;
	}
	public DataActivity getDataActivity() {
		return dataActivity;
	}
	public void setDataActivity(DataActivity dataActivity) {
		this.dataActivity = dataActivity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
