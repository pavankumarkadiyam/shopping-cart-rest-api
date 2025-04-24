package com.pavan.shoppingcart.sevice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pavan.shoppingcart.dto.OrderDTO;
import com.pavan.shoppingcart.helpers.CartToOrderHelper;
import com.pavan.shoppingcart.models.Cart;
import com.pavan.shoppingcart.models.Order;
import com.pavan.shoppingcart.models.User;
import com.pavan.shoppingcart.repository.CartRepo;
import com.pavan.shoppingcart.repository.OrderRepo;

@Service
public class OrderService {
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private OrderRepo orderRepo;

	@Transactional
	public ResponseEntity<OrderDTO> placeOrder(User user) {
		Optional <Cart> optionalCart = cartRepo.findByUser(user);
		if(optionalCart.isPresent() && !optionalCart.get().getCartItems().isEmpty()) {
			Cart cart = optionalCart.get();
			Order order = new Order();
			order.setOrderItems(CartToOrderHelper.getOrderItems(cart.getCartItems()));
			boolean stockValidation = order.getOrderItems().stream().anyMatch(orderItem -> orderItem.getProduct().getStock()<orderItem.getQuantity());
			if(stockValidation) {
				return new ResponseEntity<OrderDTO>(HttpStatus.BAD_REQUEST);
			}
			else {
				order.getOrderItems().forEach(orderItem -> {
					orderItem.getProduct().setStock(orderItem.getProduct().getStock()-orderItem.getQuantity());
				});
			}
			order.setOrderDate(LocalDateTime.now());
			order.setTotalOrderPrice(cart.getCartPrice());
			order.setStatus("OrderPlaced");
			order.setUser(user);
			Order savedOrder = orderRepo.save(order);
			cart.getCartItems().clear();
			cartRepo.save(cart);
			return new ResponseEntity<OrderDTO>(OrderDTO.orderToDTO(savedOrder),HttpStatus.OK);
		}
		return new ResponseEntity<OrderDTO>(HttpStatus.BAD_REQUEST);
	}
	
	@Transactional
	public ResponseEntity<List<OrderDTO>> getOrders(User user) {
		Optional <List<Order>> optionalOrder = orderRepo.findAllByUser(user);
		if(optionalOrder.isPresent()){
			return new ResponseEntity<List<OrderDTO>>(OrderDTO.AllOrdersDto(optionalOrder.get()),HttpStatus.OK);
		}
		return new ResponseEntity<List<OrderDTO>>(HttpStatus.BAD_REQUEST);
	}

}
