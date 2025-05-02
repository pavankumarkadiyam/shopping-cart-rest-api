package com.pavan.shoppingcart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pavan.shoppingcart.filters.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
	@Autowired
	private CustomUserDetailService userDetailService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST,"/api/register", "/api/login").permitAll().anyRequest().authenticated());
		http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
		http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider =  new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(userDetailService);
		return authenticationProvider;
	}
	
	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfig) throws Exception
	{
		return authenticationConfig.getAuthenticationManager();
	}
}
