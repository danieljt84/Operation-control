package com.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.Promoter;
import com.model.Team;
import com.util.Status;

@Repository
public class PromoterRepositoryImp {

	@Autowired
	PromoterRepository promoterRepository;

	public Promoter checkPromoter( String namePromoter) {
		Promoter promoter = promoterRepository.findByName(namePromoter);
		if (promoter == null) {
			Promoter newPromoter = new Promoter(namePromoter);
			promoter = promoterRepository.save(newPromoter);
		}
		return promoter;
	}

	public void updateIfHasUpdate(Promoter promoter,Team team,String cpf, String idSystem) {
		try {
		     promoter.setTeam(team);
		     promoter.setStatus(Status.ATIVO);
		     promoter.setIdSystem(Long.parseLong(idSystem));
		     promoterRepository.save(promoter);
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
   public void updateStatusForAll(List<Promoter> promoters) {
	   List<Long> idPromoters = promoters.stream().map(promoter -> promoter.getId()).collect(Collectors.toList());
	   promoterRepository.updateStatusToInativo(idPromoters);
   }
}
