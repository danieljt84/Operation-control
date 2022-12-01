package com.model.finance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.model.Activity;

@Entity
@DiscriminatorValue(value = "PER_DAY_IN_WEEK")
public class BonusPerDayInWeek extends Bonus {
	
	@ManyToOne
	private DataActivity dataActivity;
	private Integer daysInWeek;
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
	public Integer getDaysInWeek() {
		return daysInWeek;
	}
	public void setDaysInWeek(Integer daysInWeek) {
		this.daysInWeek = daysInWeek;
	}
}
