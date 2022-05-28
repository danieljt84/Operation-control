package com.controller.dto;

public class CargaHorariaDTO {

	private String data;
	private String time;
	private String promotor;
	private String status;
	private String duracao;

	public CargaHorariaDTO(String data,String time, String promotor, String status, String duracao) {
		super();
		this.data = data;
		this.time = time;
		this.promotor = promotor;
		this.status = status;
		this.duracao = duracao;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public String getPromotor() {
		return promotor;
	}
	public void setPromotor(String promotor) {
		this.promotor = promotor;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDuracao() {
		return duracao;
	}
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
}
