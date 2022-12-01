package com.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.Promoter;
import com.model.Team;
import com.repository.PromoterRepository;
import com.util.Status;

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
	
	@Transactional
	public void updateStatusToInativo(Set<Long> idPromoterts) {
		try {
			promoterRepository.updateStatusToInativo(idPromoterts);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public Promoter checkPromoter( String namePromoter,Long idSystem) {
		try {
			Promoter promoter = promoterRepository.findByNameOrIdSystem(namePromoter,idSystem);
			if (promoter == null && namePromoter!=null) {
				Promoter newPromoter = new Promoter(namePromoter.toUpperCase());
				promoter = promoterRepository.save(newPromoter);
			}
			return promoter;
		}catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public void updateIfHasUpdate(Promoter promoter,Team team,String cpf, long idSystem) {
		try {
		     promoter.setTeam(team);
		     promoter.setStatus(Status.ATIVO);
		     promoter.setIdSystem(idSystem);
		     promoterRepository.save(promoter);
		}catch (Exception e) {
		    
		}
	}
}
