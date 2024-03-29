package com.service.operation;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Project;
import com.model.Shop;
import com.repository.operation.ShopRepository;

@Service
public class ShopService {

	@Autowired
	ShopRepository shopRepository;
	
	public Shop checkShop(String name, Long idSystem,String project) {
		try {
			Shop shop = shopRepository.findByIdSystem(idSystem);
			if(shop == null && name!=null) {
				shop = new Shop(name.toUpperCase());
				if(idSystem != null) shop.setIdSystem(idSystem);
				shop.setProject(Project.getEnum(project));
				shopRepository.save(shop);
			}			
			return shop;
		}catch (Exception e) {
			return null;
		}
	}
	
	public Shop findById(Long id) {
		Optional<Shop> optional = shopRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			throw new EntityNotFoundException("LOJA -"+id+"- NÃO ENCONTRADA");
		}
	}
}
