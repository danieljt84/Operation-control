package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.service.PromoterService;

@RestController(value = "/promoter")
public class PromoterController {
	
	@Autowired
	PromoterService promoterService;
	
	@GetMapping(value = "/findbyteam/{name}")
	public ResponseEntity findByTeam(@PathVariable String name) {
		return null;
	}
	
	

}
