package com.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.PromoterService;

@RestController()
@RequestMapping(value = "/promoter")
public class PromoterController {
	
	@Autowired
	PromoterService promoterService;
	
	@GetMapping(value = "/team/{name}")
	public ResponseEntity findByTeam(@PathVariable String name) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(promoterService.findByTeam(name));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	@GetMapping()
	public ResponseEntity findAll() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(promoterService.findAll());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	

}
