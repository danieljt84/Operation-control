package com.controller.dto;

public class CountActivityCompleteDTO {
	
	private String teamName;
	private Integer countComplete;
	private Integer countMissing;

	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Integer getCountComplete() {
		return countComplete;
	}
	public void setCountComplete(Integer count) {
		this.countComplete = count;
	}
	public Integer getCountMissing() {
		return countMissing;
	}
	public void setCountMissing(Integer countMissing) {
		this.countMissing = countMissing;
	}
}

