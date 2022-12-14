package com.model.finance;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(schema = "finance")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 1, discriminatorType = DiscriminatorType.STRING)
public abstract class Bonus {
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	private String responsible;
	private LocalDate createdAt;
	private LocalDate finishedAt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResponsible() {
		return responsible;
	}
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDate getFinishedAt() {
		return finishedAt;
	}
	public void setFinishedAt(LocalDate finishedAt) {
		this.finishedAt = finishedAt;
	}
}
