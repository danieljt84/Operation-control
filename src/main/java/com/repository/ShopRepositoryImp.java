package com.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class ShopRepositoryImp {
  
	@PersistenceContext
	EntityManager entityManager;
	
	public void findByName() {
		
	}
}
