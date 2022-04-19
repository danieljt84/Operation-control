package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Promoter;
import com.repository.PromoterRepository;

@Service
public class PromoterService {
	@Autowired
	PromoterRepository promoterRepository;
	
	public List<Promoter> findByTeam(){
		return null;
	}
	
	
}
