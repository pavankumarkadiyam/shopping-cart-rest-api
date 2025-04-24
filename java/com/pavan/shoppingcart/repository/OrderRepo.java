package com.pavan.shoppingcart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.shoppingcart.models.Order;
import com.pavan.shoppingcart.models.User;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

	Optional<Order> findByUser(User user);
	
	Optional<List<Order>> findAllByUser(User user);

}
