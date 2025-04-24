package com.pavan.shoppingcart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.shoppingcart.dto.UserRegistrationDTO;
import com.pavan.shoppingcart.models.User;
import com.pavan.shoppingcart.repository.RoleRepo;
import com.pavan.shoppingcart.repository.UserRepo;

@RestController
@RequestMapping("/api/register")
public class UserRegistration {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping
	public ResponseEntity<UserRegistrationDTO> newUser(@RequestBody UserRegistrationDTO userdto){
			User newUser = new User();
			newUser.setUsername(userdto.getUsername());
			newUser.setPassword(passwordEncoder.encode(userdto.getPassword()));
			//System.err.print(roleRepo.findById(userdto.getRoleid()).get());
			newUser.setRole(roleRepo.findById(userdto.getRoleid()).get());
			try {
				User savedUser = userRepo.save(newUser);
				return new ResponseEntity<UserRegistrationDTO>(new UserRegistrationDTO(savedUser.getUsername(),"Encrypted",savedUser.getRole().getId()),HttpStatus.CREATED);
			}
			catch(Exception e) {
				return new ResponseEntity<UserRegistrationDTO>(new UserRegistrationDTO(userdto.getUsername(),"Encrypted",0),HttpStatus.BAD_REQUEST);
			}
	}

}
