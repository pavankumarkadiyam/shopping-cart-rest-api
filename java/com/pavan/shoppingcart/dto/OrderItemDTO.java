package com.pavan.shoppingcart.dto;

import org.springframework.stereotype.Component;

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
public class OrderItemDTO {

	private Long id;
	private String product;
	private Double price;
	private Integer quantity;
	
	public static OrderItemDTO orderItemToDTO(OrderItem orderItem) {
		OrderItemDTO orderItemDto = new OrderItemDTO();
		orderItemDto.setId(orderItem.getId());
		orderItemDto.setPrice(orderItem.getPrice());
		orderItemDto.setProduct(orderItem.getProduct().getName());
		orderItemDto.setQuantity(orderItem.getQuantity());
		return orderItemDto;
	}
}
