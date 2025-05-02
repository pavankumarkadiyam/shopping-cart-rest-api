package com.pavan.shoppingcart.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pavan.shoppingcart.models.User;

public class CustomUserDetails implements UserDetails {
	
	private final User user;
	public CustomUserDetails(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRole().getName()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

}
