package com.pavan.shoppingcart.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component
@Data
@Setter
@Getter
public class LoginForm {
	private String username;
	private String password;
}