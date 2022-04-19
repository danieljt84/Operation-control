package com.service;

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

import com.model.Role;
import com.model.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;

@Service 
public class UserService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getUser(username);
		if(user == null) {
			throw new UsernameNotFoundException("Usuario não encontrado no BD");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

	
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	public Role saveRole(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	public void addRoleToUser(String username, String nameRole) {
		User user = userRepository.findByUsername(username);
		Role role = roleRepository.findByname(nameRole);
		user.getRoles().add(role);
	}

	
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}

	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

}
