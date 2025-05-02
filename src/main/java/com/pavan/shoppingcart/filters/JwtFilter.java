package com.pavan.shoppingcart.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pavan.shoppingcart.security.CustomUserDetailService;
import com.pavan.shoppingcart.sevice.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CustomUserDetailService userDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = null;
		header = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = null;
		if(header != null && header.startsWith("Bearer")) {
			token = header.substring(7);
		}
		if(token != null) {
			String username = jwtService.extractUsername(token);
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails user = userDetailService.loadUserByUsername(username);
				if(user != null && jwtService.isTokenValid(token)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
	filterChain.doFilter(request, response);
	}

}
