package com.model.finance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.model.Activity;

@Entity
@DiscriminatorValue(value = "PER_DAY_IN_WEEK")
public class BonusPerDayInWeek extends Bonus {
	
	private Map<Activity, Long> activity_day;
	private LocalDate start;
	private LocalDate end;
	
	public Map<Activity, Long> getActivity_day() {
		return activity_day;
	}
	public void setActivity_day(Map<Activity, Long> activity_day) {
		this.activity_day = activity_day;
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
