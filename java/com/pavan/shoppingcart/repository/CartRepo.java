package com.pavan.shoppingcart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.shoppingcart.models.Cart;
import com.pavan.shoppingcart.models.User;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

	Optional<Cart> findByUser(User user);

}
