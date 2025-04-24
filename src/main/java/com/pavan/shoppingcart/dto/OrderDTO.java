package com.pavan.shoppingcart.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pavan.shoppingcart.models.Order;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Data
@NoArgsConstructor
@Setter
@Getter
public class OrderDTO {
	
	private Long id;
	private LocalDateTime orderDate;
	private String status;
	private String user;
	private List<OrderItemDTO> orderItems;
	private Double totalOrderPrice;
	
	public static OrderDTO orderToDTO(Order order) {
		OrderDTO orderDto = new OrderDTO();
		orderDto.setId(order.getId());
		orderDto.setOrderDate(order.getOrderDate());
		orderDto.setStatus(order.getStatus());
		orderDto.setUser(order.getUser().getUsername());
		orderDto.setOrderItems(order.getOrderItems().stream().map(orderItem -> OrderItemDTO.orderItemToDTO(orderItem)).toList());
		orderDto.setTotalOrderPrice(order.getTotalOrderPrice());
		return orderDto;
	}

	public static List<OrderDTO> AllOrdersDto(List<Order> orders) {
		
		return orders.stream().map(order -> orderToDTO(order)).toList();
	}

}
