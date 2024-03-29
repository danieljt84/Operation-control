package com.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.model.User;
import com.repository.UserRepository;
import com.service.auth.TokenService;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UserRepository repository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService,UserRepository repository) {
		this.tokenService= tokenService;
		this.repository = repository;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		
		boolean valido = tokenService.isValidoToken(token);
		
		if(valido) {
			autenticarCliente(token);
		}
		filterChain.doFilter(request, response);
	}
	
	private void autenticarCliente(String token) {
		Long idUsuario =  tokenService.getIdUsuario(token);
		User usuario = repository.findById(idUsuario).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}
	private String recuperarToken(HttpServletRequest request){
		
		String token = request.getHeader("Authorization");
		if(token == null|| token.isEmpty()) {
		 return null;
		}
		return token;
	}

}
