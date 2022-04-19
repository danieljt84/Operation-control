package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.TokenDTO;
import com.model.User;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private com.service.TokenService tokenService;
	
	@PostMapping
	public ResponseEntity login(@RequestBody User user) {
UsernamePasswordAuthenticationToken dadosLogin = converter(user);
		
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	public UsernamePasswordAuthenticationToken converter(User user) {
		return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
	}


}
