package com.util;

public enum ProjectAdapter {
	
	FIXO_RJ("4pmktcfixo"),COMPARTILHADO_RJ("4pmktcompartilhado"),COMPARTILHADO_SP("4pmktcompartilhadosp")
	,COMPARTILHADO_ES("4pmktcompartilhadoes"),COMPARTILHADO_BA("4pmktcompartilhadoba");
	
	String description;
	
	private ProjectAdapter(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
