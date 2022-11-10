package com.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.Brand;
@Repository
public class BrandRepositoryImp {
	@Autowired
	BrandRepository brandRepository;

	public Brand checkBrand(String name) {
		Brand brand = brandRepository.findByName(name.toUpperCase());
		if(brand == null) {
			brand = new Brand(name.toUpperCase());
			brandRepository.save(brand);
		}
		return brand;
	}

}
