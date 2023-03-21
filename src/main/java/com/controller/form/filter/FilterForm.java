package com.controller.form.filter;

import java.util.List;

import com.controller.dto.operation.ProjectDTO;
import com.controller.dto.operation.ShopDTO;

public class FilterForm {
	
	private List<Long> shops;
	private List<Long> projects;
	private List<Long> chains;
	
	public List<Long> getShops() {
		return shops;
	}
	public void setShops(List<Long> shops) {
		this.shops = shops;
	}
	public List<Long> getProjects() {
		return projects;
	}
	public void setProjects(List<Long> projects) {
		this.projects = projects;
	}
	public List<Long> getChains() {
		return chains;
	}
	public void setChains(List<Long> chains) {
		this.chains = chains;
	}
	
}
