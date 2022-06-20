package com.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	public Map<String,Object> gerarToken(Authentication authentication) {
		User logado = (User) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong("1000000"));
		Map<String,Object> _return = new HashMap<String,Object>();
        _return.put("token", Jwts.builder().setSubject(logado.getId().toString()).setIssuedAt(hoje)
				.setExpiration(dataExpiracao).signWith(SignatureAlgorithm.HS256, "danielmoreira").compact());
        _return.put("user",logado);
		
		 return _return;
	}

	public boolean isValidoToken(String token) {
		try {
			Jwts.parser().setSigningKey("danielmoreira").parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public Long getIdUsuario(String token) {
		Claims body = Jwts.parser().setSigningKey("danielmoreira").parseClaimsJws(token).getBody();
  		return Long.parseLong(body.getSubject());
	}

}
