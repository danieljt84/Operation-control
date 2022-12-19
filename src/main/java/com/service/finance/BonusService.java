package com.service.finance;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.finance.Bonus;
import com.model.finance.DataActivity;
import com.repository.finance.BonusRepository;

@Service
public class BonusService {

	@Autowired
	BonusRepository bonusRepository;
	
	
	public Bonus save(Bonus bonus) {
	   return bonusRepository.save(bonus);
	}
	
	public Bonus findById(Long id) {
		Optional<Bonus> optional = bonusRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new EntityNotFoundException("BONIFICAÇÃO -" + id + "- NÃO ENCONTRADO");
		}
	}
	
	public List<Bonus> findAll(){
		return bonusRepository.findAll();
	}
	
	public void delete(Long id) {
		Optional<Bonus> optional = bonusRepository.findById(id);
		if(optional.isPresent()) {
			bonusRepository.delete(optional.get());
		}else {
			throw new EntityNotFoundException("BONIFICAÇÃO COM ID:" + id + " - NÃO ENCONTRADO");
		}
	}
}
