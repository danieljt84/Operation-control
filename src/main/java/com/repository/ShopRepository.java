package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.model.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long>{
	
	@Query(value = "select s from Shop s where upper(s.name) like CONCAT('%',upper(:name),'%')" )
	Shop findByName(@Param(value = "name") String name);

}
