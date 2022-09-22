package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {

	Brand findByName(String name);
	
	@Query(value = "select b from Brand b where upper(b.name) like CONCAT('%',upper(:name),'%')")
    Brand findByNameContaining(@Param(value = "name") String name);
}
