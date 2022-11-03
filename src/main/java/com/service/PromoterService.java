package com.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Promoter;
import com.repository.PromoterRepository;

@Service
public class PromoterService {
	@Autowired
	PromoterRepository promoterRepository;
	
	public List<Promoter> findByTeam(String nameTeam){
		return promoterRepository.findByNameTeam(nameTeam);
	}
	   
	public List<Promoter> findAll(){
	  return promoterRepository.findAll();
	}
	
	public void updateStatusToInativo(Set<Long> idPromoterts) {
		try {
			promoterRepository.updateStatusToInativo(idPromoterts);

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
