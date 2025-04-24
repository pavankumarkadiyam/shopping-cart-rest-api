package com.pavan.shoppingcart.helpers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pavan.shoppingcart.models.CartItem;
import com.pavan.shoppingcart.models.OrderItem;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Data
@NoArgsConstructor
@Setter
@Getter
public class CartToOrderHelper {

	public static List<OrderItem> getOrderItems(List <CartItem> cartItems)
	{
		List<OrderItem> orderItems = new ArrayList<>();
		cartItems.forEach(cartItem -> {
			orderItems.add(CartToOrderHelper.CarttoOrderItem(cartItem));
		});
		return orderItems;
	}
	public static OrderItem CarttoOrderItem(CartItem cartItem) {
		OrderItem orderItem = new OrderItem();
		orderItem.setProduct(cartItem.getProduct());
		orderItem.setQuantity(cartItem.getQuantity());
		orderItem.setPrice(cartItem.getCartItemPrice());
		return orderItem;
	}
	
}
