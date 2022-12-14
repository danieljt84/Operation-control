package com.controller.dto.operation;

public class PercentByDateAndTeamDTO {
	
	private String promoter;
	private Integer realizado;
	private Integer previsto;
	public String getPromoter() {
		return promoter;
	}
	public void setPromoter(String promoter) {
		this.promoter = promoter;
	}
	public Integer getRealizado() {
		return realizado;
	}
	public void setRealizado(Integer realizado) {
		this.realizado = realizado;
	}
	public Integer getPrevisto() {
		return previsto;
	}
	public void setPrevisto(Integer previsto) {
		this.previsto = previsto;
	}
}
