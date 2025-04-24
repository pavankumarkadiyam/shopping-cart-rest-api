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
	
	public User getCurrentUser() {
		CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getUser();	
	}
	
	//get current user cart
	@GetMapping
	public ResponseEntity<CartDTO> getCart(){
		return cartService.getUserCart(getCurrentUser());
	}
	
	@PostMapping("/{productId}")
	public  ResponseEntity<CartDTO> addItemToCart(@PathVariable long productId){
		return cartService.addProductToCart(getCurrentUser(),productId);
	}
	
	@PutMapping("/update")
	public ResponseEntity<CartDTO> updateCart(@RequestParam("productid") long productId, @RequestParam("quantity") int quantity){
		return cartService.update(getCurrentUser(), productId,quantity);
	}
	
	//remove a cart item
	@DeleteMapping("/remove/{productid}")
	public ResponseEntity<CartDTO> deleteItem(@PathVariable long productid){
		return cartService.deleteItem(getCurrentUser(),productid);
	}
	
	@DeleteMapping("/clear")
	public ResponseEntity<CartDTO> deleteAll()
	{
		return cartService.deleteAllItems(getCurrentUser());
	}
}
