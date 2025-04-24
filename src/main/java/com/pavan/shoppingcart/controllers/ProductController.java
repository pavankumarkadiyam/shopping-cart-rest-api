package com.pavan.shoppingcart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.shoppingcart.models.Product;
import com.pavan.shoppingcart.sevice.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	//get all products
	@GetMapping
	public ResponseEntity<List<Product>> getProducts(){
		return productService.getProducts();
	}
	
	//get product by id
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable long id){
		return productService.getProduct(id);
	}
	
	//add new product
	@PostMapping
	public ResponseEntity<Product> newProduct(@RequestBody Product product){
		return productService.create(product);
	}
	
	//update product by id
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product){
		return productService.update(id,product);
	}
	
	//delete product by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Product> deletProduct(@PathVariable long id){
		return productService.delete(id);
	}
}
