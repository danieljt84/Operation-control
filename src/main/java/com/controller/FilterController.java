package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Brand;
import com.service.FilterService;
import com.service.operation.BrandService;

@RestController
@RequestMapping("/filter")
public class FilterController {

	@Autowired
	FilterService filterService;
	@Autowired
	BrandService brandService;

	@GetMapping("/activation")
	@Cacheable("activation")
	public ResponseEntity getAllValuesPossibleToActivation(@RequestParam String initialDate,
			@RequestParam String finalDate, @RequestParam(name ="idsBrand") List<Long> idsBrand) {
		try {
			List<Brand> brands = idsBrand.stream().map(id -> brandService.findById(id)).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK)
					.body(filterService.getAllValuesPossibleToFilterToActivation(
							LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
							LocalDate.parse(finalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), brands));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}


}
