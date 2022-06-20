package com.util.model;

import com.model.Promoter;

public class AjudaDeCusto {
	
	private Promoter promoter;
	private RelacaoDias relacaoDias;
	private int daysWorkings;
	private int diasUteis;
	private int diasDescontoVA;
	public Promoter getPromoter() {
		return promoter;
	}
	public void setPromoter(Promoter promoter) {
		this.promoter = promoter;
	}
	public RelacaoDias getRelacaoDias() {
		return relacaoDias;
	}
	public void setRelacaoDias(RelacaoDias relacaoDias) {
		this.relacaoDias = relacaoDias;
	}
	public int getDaysWorkings() {
		return daysWorkings;
	}
	public void setDaysWorkings(int daysWorkings) {
		this.daysWorkings = daysWorkings;
	}
	public int getDiasUteis() {
		return diasUteis;
	}
	public void setDiasUteis(int diasUteis) {
		this.diasUteis = diasUteis;
	}
	public int getDiasDescontoVA() {
		return diasDescontoVA;
	}
	public void setDiasDescontoVA(int diasDescontoVA) {
		this.diasDescontoVA = diasDescontoVA;
	}


}
