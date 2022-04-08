package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Promoter;

@Repository
public interface PromoterRepository extends JpaRepository<Promoter, Long> {
	
	Promoter findByName(String name);
}
