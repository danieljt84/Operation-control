package com.controller.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CountActivityCompleteByPromoterDTO {
	
	private String teamName;
	private String promoterName;
	private Integer countComplete;
	private Integer countMissing;
	private Integer countTotal;
	private Integer percent;
	
	public CountActivityCompleteByPromoterDTO(CountActivityCompleteByPromoterDTO countActivityCompleteByPromoterDTO) {
		this.teamName = countActivityCompleteByPromoterDTO.getTeamName();
		this.promoterName = countActivityCompleteByPromoterDTO.getPromoterName();
		this.countComplete = countActivityCompleteByPromoterDTO.getCountComplete();
		this.countMissing = countActivityCompleteByPromoterDTO.getCountMissing();
		this.countTotal = countActivityCompleteByPromoterDTO.getCountTotal();
		this.percent = countActivityCompleteByPromoterDTO.getPercent();
	}

	public CountActivityCompleteByPromoterDTO() {
	}
	
	public Integer getPercent() {
		return percent;
	}
	public void setPercent(Integer percent) {
		this.percent = percent;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getPromoterName() {
		return promoterName;
	}
	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}
	public Integer getCountComplete() {
		return countComplete;
	}
	public void setCountComplete(Integer countComplete) {
		this.countComplete = countComplete;
	}
	public Integer getCountMissing() {
		return countMissing;
	}
	public void setCountMissing(Integer countMissing) {
		this.countMissing = countMissing;
	}
	public Integer getCountTotal() {
		return countTotal;
	}
	public void setCountTotal(Integer countTotal) {
		this.countTotal = countTotal;
	}
	
	public static List<CountActivityCompleteByPromoterDTO> orderByPercent(List<CountActivityCompleteByPromoterDTO> datas){
		List<CountActivityCompleteByPromoterDTO> newDatas = new ArrayList<>();
		int count = datas.size();
		for(int i =0; i<count;i++) {
			CountActivityCompleteByPromoterDTO element = datas.stream().max(Comparator.comparing(CountActivityCompleteByPromoterDTO::getPercent)).get();
		    newDatas.add(new CountActivityCompleteByPromoterDTO(element));
		    datas.remove(element);
		}
		return newDatas;
	}
}
