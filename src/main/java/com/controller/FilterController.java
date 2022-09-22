package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Brand;
import com.service.BrandService;
import com.service.FilterService;

@RestController
@RequestMapping("/filter")
public class FilterController {

	@Autowired
	FilterService filterService;
	@Autowired
	BrandService brandService;

	@GetMapping("/activation")
	public ResponseEntity getAllValuesPossibleToActivation(@RequestParam String initialDate,
			@RequestParam String finalDate, @RequestParam String nameBrand) {
		try {
			Brand brand = brandService.getBrandByNameContaining(nameBrand);
			return ResponseEntity.status(HttpStatus.OK)
					.body(filterService.getAllValuesPossibleToFilterToActivation(
							LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
							LocalDate.parse(finalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), brand.getId()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
