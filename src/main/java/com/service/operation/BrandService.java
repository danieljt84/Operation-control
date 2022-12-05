package com.service.operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.model.Brand;
import com.repository.operation.BrandRepository;

@Service
public class BrandService {
	
	@Autowired
	BrandRepository brandRepository;
	
	public List<Brand> getBrands() throws Exception{
		try {
			return brandRepository.findAll();

		}catch (Exception e) {
			throw new Exception("ERRO NO CARREGAMENTO DA LISTA");
		}
	}
	
	public Brand getBrandByNameContaining(String name) throws Exception {
		try {
			return brandRepository.findByNameContaining(name);
		}catch (Exception e) {
			throw new Exception("ERRO NO CARREGAMENTO DA BRAND");
		}
	}
	
	public Brand checkBrand(String name) {
		try {
			Brand brand = brandRepository.findByName(name);
			if(brand == null && name!=null) {
				brand = new Brand(name.toUpperCase());
				brandRepository.save(brand);
			}
			return brand;
		}catch (Exception e) {
			return null;
		}
		
	}

}
