package com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.BrandService;
import com.service.DataTaskService;

@RestController
@RequestMapping("/")
public class HomeController {
	@Autowired
	DataTaskService dataTaskService;
	@Autowired
	BrandService brandService;
	
	@GetMapping
	public void home() {
		System.out.println("oi");
	}

	@GetMapping(value = "/findincompleto")
	public ResponseEntity findByShopAndGradeAndSituationEqualsIncompleto(@RequestParam String shop,
			@RequestParam String grade) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(dataTaskService.findByShopAndGradeAndSituationEqualsIncompleto(shop,grade));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
	}
	
	@GetMapping(value = "/brand")
	public ResponseEntity getBrands() {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(brandService.getBrands());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
	}
}
