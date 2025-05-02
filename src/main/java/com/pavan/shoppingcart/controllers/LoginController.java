package com.pavan.shoppingcart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.shoppingcart.dto.LoginForm;
import com.pavan.shoppingcart.security.CustomUserDetails;
import com.pavan.shoppingcart.sevice.JwtService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
    
    public LoginController(){
    	
    }
	
	@PostMapping
	public String authenticateUser(@RequestBody LoginForm loginForm) throws Exception {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(),loginForm.getPassword()));
		if(authentication.isAuthenticated()) {
			CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
			String token = jwtService.generateToken(userDetails);
			return token;
		}
		throw new UsernameNotFoundException("User not found with "+loginForm.getUsername());
	}
	

}
