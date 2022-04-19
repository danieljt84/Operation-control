package com.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.repository.UserRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserRepository userRepository;
	
	 public SecurityConfig(UserRepository userRepo) {
	        this.userRepository = userRepo;
	    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> userRepository
                .findByUsername(username)
                .orElseThrow(
                    () -> new UsernameNotFoundException(
                        format("User: %s, not found", username)
                    )
                ));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO configure web security
    }

}