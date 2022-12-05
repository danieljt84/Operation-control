package com.controller;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controller.dto.TokenDTO;
import com.controller.dto.UserDTO;
import com.model.User;
import com.service.auth.TokenService;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	ModelMapper modelMapper;

	@PostMapping
	public ResponseEntity login(@RequestBody User user) {
		UsernamePasswordAuthenticationToken dadosLogin = converter(user);

		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			Map<String, Object> user_token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok().body(new TokenDTO((String) user_token.get("token")
					,"Bearer"
					,new UserDTO().convertToDTO((User) user_token.get("user"), modelMapper)));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	public UsernamePasswordAuthenticationToken converter(User user) {
		return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
	}

}
