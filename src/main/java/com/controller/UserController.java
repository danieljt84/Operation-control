package com.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Role;
import com.model.User;
import com.service.UserService;

@RestController
@RequestMapping("/user")

public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> findAll(){
		return ResponseEntity.ok().body(userService.getUsers());
	}
	
	@PostMapping(value = "/save")
	public ResponseEntity save(@RequestBody User user){
		return ResponseEntity.ok().body(userService.saveUser(user));
	}
	@PostMapping(value = "/role/save")
	public ResponseEntity saveRole(@RequestBody Role role){
		return ResponseEntity.ok().body(userService.saveRole(role));
	}

}
