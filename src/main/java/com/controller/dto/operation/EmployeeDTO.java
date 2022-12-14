package com.controller.dto.operation;

public class EmployeeDTO {
	private Long id;
	private String name;
	private String cpf;
	private String rg;
	private String numeroCtps;
	private String serieCtps;
	private String empresa;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getNumeroCtps() {
		return numeroCtps;
	}
	public void setNumeroCtps(String numeroCtps) {
		this.numeroCtps = numeroCtps;
	}
	public String getSerieCtps() {
		return serieCtps;
	}
	public void setSerieCtps(String serieCtps) {
		this.serieCtps = serieCtps;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
}
