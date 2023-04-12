package com.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.service.auth.TokenService;

@Component
public class AuthInterceptor  implements HandlerInterceptor {

	@Autowired
	TokenService tokenService;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		
        String token = request.getHeader("Authorization");

        if (token != null) {
            boolean isValid = tokenService.isValidToken(token);
            
            if (isValid) {
                return true;
            }
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }
}
