package com.service.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.model.User;

import com.repository.UserRepository;

@Service 
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return repository.save(user);
	}
	
	public User getUser(String username) {
		return repository.findByUsername(username).get();
	}

	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
