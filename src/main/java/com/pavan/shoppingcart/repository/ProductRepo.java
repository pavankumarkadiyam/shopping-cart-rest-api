package com.pavan.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.shoppingcart.models.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

}
