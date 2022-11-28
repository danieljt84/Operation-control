package com.model.finance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.model.Activity;
import com.util.finance.BonusPerValueType;

@DiscriminatorValue(value = "PER_PRICE")
@Entity
public class BonusPerPrice extends Bonus {
	
	private Map<Activity, BigDecimal> activity_Value;
	@Enumerated(EnumType.STRING)
	private BonusPerValueType bonusPerValueType;
	private LocalDate start;
	private LocalDate end;
	
	public Map<Activity, BigDecimal> getActivity_Value() {
		return activity_Value;
	}
	public void setActivity_Value(Map<Activity, BigDecimal> activity_Value) {
		this.activity_Value = activity_Value;
	}
	public BonusPerValueType getBonusPerValueType() {
		return bonusPerValueType;
	}
	public void setBonusPerValueType(BonusPerValueType bonusPerValueType) {
		this.bonusPerValueType = bonusPerValueType;
	}
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
}
