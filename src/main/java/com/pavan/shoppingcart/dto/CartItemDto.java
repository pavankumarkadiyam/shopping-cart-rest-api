package com.pavan.shoppingcart.dto;

import org.springframework.stereotype.Component;

import com.pavan.shoppingcart.models.CartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CartItemDto {

	private Long productId;
	private String product;
	private Integer quantity;
	private Double itemPrice;
	
	public static CartItemDto CartItemToDto(CartItem cartItem) {
		CartItemDto cartItemdto = new CartItemDto();
		cartItemdto.setProduct(cartItem.getProduct().getName());
		cartItemdto.setQuantity(cartItem.getQuantity());
		cartItemdto.setItemPrice(cartItem.getCartItemPrice());
		cartItemdto.setProductId(cartItem.getProduct().getId());
		return cartItemdto;
	}
}
