package com.service.operation;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.dto.operation.ShopDTO;
import com.model.Project;
import com.model.Shop;
import com.repository.operation.ShopRepository;

@Service
public class ShopService {

	@Autowired
	ShopRepository shopRepository;
	@Autowired
	ModelMapper modelMapper;
	
	public Shop checkShop(String name, Long idSystem,Project project) {
		try {
			Shop shop = shopRepository.findByIdSystem(idSystem);
			if(shop == null && name!=null) {
				shop = new Shop(name.toUpperCase());
				if(idSystem != null) shop.setIdSystem(idSystem);
				shop.setProject(project);
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
	
	public List<Shop> findAllById(List<Long> ids) {
		List<Shop> shops = shopRepository.findAllById(ids);
		if(!shops.isEmpty()) {
			return shops;
		}else {
			throw new EntityNotFoundException();
		}
	}

}
