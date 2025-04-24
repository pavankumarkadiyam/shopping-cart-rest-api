package com.pavan.shoppingcart.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pavan.shoppingcart.models.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Data
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class CartDTO {
	
	private String username;
	private List<CartItemDto> cartItems;
	private Double cartPrice;
	
	public static CartDTO cartToDTO(Cart cart) {
		CartDTO cartDTO = new CartDTO();
		cartDTO.setUsername(cart.getUser().getUsername());
		cartDTO.setCartItems(cart.getCartItems().stream().map(item -> CartItemDto.CartItemToDto(item)).toList());
		cartDTO.setCartPrice(cart.getCartPrice());
		return cartDTO;
	}

}
