package com.pavan.shoppingcart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.shoppingcart.models.Cart;
import com.pavan.shoppingcart.models.CartItem;
import com.pavan.shoppingcart.models.Product;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {


	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

	Optional <List<CartItem>> findByCart(Cart cart);

}
