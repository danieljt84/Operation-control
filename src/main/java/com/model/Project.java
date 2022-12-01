package com.model;

public enum Project {
	FIXO_RJ("4pmktcfixo"),COMPARTILHADO_RJ("4pmktcompartilhado"),COMPARTILHADO_SP("4pmktcompartilhadosp"),COMPARTILHADO_ES("4pmktcompartilhadoes"),COMPARTILHADO_BA("4pmktcompartilhadoba"),EXCLUSIVO_RJ(""),ROYAL("royal4pmkt");
	String description;
	
	private Project(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	

    public static Project getEnum(String value) {
        if(value == null)
            throw new IllegalArgumentException();
        for(Project v : values())
            if(value.equalsIgnoreCase(v.getDescription())) return v;
        throw new IllegalArgumentException();
    }
	
}
