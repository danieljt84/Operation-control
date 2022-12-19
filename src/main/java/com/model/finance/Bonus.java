package com.model.finance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "finance")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 20, discriminatorType = DiscriminatorType.STRING)
public abstract class Bonus {
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	private String responsible;
	private LocalDate createdAt;
	private LocalDate finishedAt;
	@ManyToMany
	@JoinTable(name="bonus_data_activity",schema = "finance",
    joinColumns={@JoinColumn(name="bonus_id",
     referencedColumnName="id")},
    inverseJoinColumns={@JoinColumn(name="data_activity_id",
      referencedColumnName="id")})
	private List<DataActivity> datasActivity;
	
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
	public List<DataActivity> getDatasActivity() {
		return datasActivity;
	}
	public void setDatasActivity(List<DataActivity> datasActivity) {
		this.datasActivity = datasActivity;
	}
	
}
