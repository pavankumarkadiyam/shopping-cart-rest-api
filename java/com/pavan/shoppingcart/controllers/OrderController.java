package com.pavan.shoppingcart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.shoppingcart.dto.OrderDTO;
import com.pavan.shoppingcart.models.User;
import com.pavan.shoppingcart.security.CustomUserDetails;
import com.pavan.shoppingcart.sevice.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/placeorder")
	public ResponseEntity<OrderDTO> placeOrder()
	{
		CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		return orderService.placeOrder(user);
	}
	
	@GetMapping
	public ResponseEntity<List<OrderDTO>> getOrders()
	{
		CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		return orderService.getOrders(user);
	}
}
