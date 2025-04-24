package com.pavan.shoppingcart.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.shoppingcart.dto.CartDTO;
import com.pavan.shoppingcart.models.User;
import com.pavan.shoppingcart.security.CustomUserDetails;
import com.pavan.shoppingcart.sevice.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartControllers {
	
	@Autowired
	private CartService cartService;
	
	//get current user cart
	@GetMapping
	public ResponseEntity<CartDTO> getCart(){
		CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		return cartService.getUserCart(user);
	}
	
	@PostMapping("/{productId}")
	public  ResponseEntity<CartDTO> addItemToCart(@PathVariable long productId){
		CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		return cartService.addProductToCart(user,productId);
	}
	
	@PutMapping("/update")
	public ResponseEntity<CartDTO> updateCart(@RequestParam("productid") long productId, @RequestParam("quantity") int quantity){
		CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		return cartService.update(user, productId,quantity);
	}
	
	//remove a cart item
	@DeleteMapping("/remove/{productid}")
	public ResponseEntity<CartDTO> deleteItem(@PathVariable long productid){
		CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		return cartService.deleteItem(user,productid);
	}
	
	@DeleteMapping("/clear")
	public ResponseEntity<CartDTO> deleteAll()
	{
		CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		return cartService.deleteAllItems(user);
	}
}
