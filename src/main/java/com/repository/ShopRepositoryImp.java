package com.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.Brand;
import com.model.Shop;

@Repository
public class ShopRepositoryImp {
  
	@Autowired
	ShopRepository shopRepository;
	
	public Shop checkShop(String name,Long idSystem) {
		Shop shop = shopRepository.findByNameOrIdSystem(name,idSystem);
		if(shop == null) {
			shop = new Shop(name);
			shopRepository.save(shop);
		}
		return shop;
	}
}
