package com.controller.dto.filter;

import java.util.List;

import com.controller.dto.operation.ProjectDTO;
import com.controller.dto.operation.ShopDTO;

public class FilterActivationDTO {

	private List<ShopDTO> shop;
	private List<ProjectDTO> project;
	private List<Object> chains;
	public List<ShopDTO> getShop() {
		return shop;
	}
	public void setShop(List<ShopDTO> shop) {
		this.shop = shop;
	}
	public List<ProjectDTO> getProject() {
		return project;
	}
	public void setProject(List<ProjectDTO> project) {
		this.project = project;
	}
	public List<Object> getChains() {
		return chains;
	}
	public void setChains(List<Object> chains) {
		this.chains = chains;
	}
	
	

	
	

}
