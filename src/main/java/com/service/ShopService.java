package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Shop;
import com.repository.ShopRepository;

@Service
public class ShopService {

	@Autowired
	ShopRepository shopRepository;
	
	public Shop checkShop(String name, Long idSystem) {
		Shop shop = shopRepository.findByName(name);
		if(shop == null && name!=null) {
			shop = new Shop(name.toUpperCase());
		}
		if(idSystem != null) shop.setIdSystem(idSystem);
		shopRepository.save(shop);
		return shop;
	}
}
