package com.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.Promoter;
import com.model.Team;

@Repository
public class PromoterRepositoryImp {
	
	@Autowired
	PromoterRepository promoterRepository;
	
	public Promoter checkPromoter(String namePromoter) {
		Promoter promoter = promoterRepository.findByName(namePromoter);
		if(promoter==null) {
			Promoter newPromoter = new Promoter(namePromoter);
			promoter = promoterRepository.save(newPromoter);
		}
		return promoter;
	}
	
	public void updateIfHasUpdateTeam(Promoter promoter,Team team) {
		promoter.setTeam(team);
		promoterRepository.save(promoter);
	}
}
