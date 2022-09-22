package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Brand;
import com.repository.BrandRepository;
import com.repository.BrandRepositoryImp;

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

}
