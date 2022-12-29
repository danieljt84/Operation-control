package com.model.finance;

import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "finance")
public class InactiveControl {
	
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private DataActivity dataActivity;
	private LocalDate startedAt;
	private LocalDate finishedAt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DataActivity getDataActivity() {
		return dataActivity;
	}
	public void setDataActivity(DataActivity dataActivity) {
		this.dataActivity = dataActivity;
	}
	public LocalDate getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(LocalDate startedAt) {
		this.startedAt = startedAt;
	}
	public LocalDate getFinishedAt() {
		return finishedAt;
	}
	public void setFinishedAt(LocalDate finishedAt) {
		this.finishedAt = finishedAt;
	}
}
