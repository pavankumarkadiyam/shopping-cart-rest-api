package com.pavan.shoppingcart.sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pavan.shoppingcart.models.Product;
import com.pavan.shoppingcart.repository.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo productRepository;
	
	//get all products
	public ResponseEntity<List<Product>> getProducts(){
		return ResponseEntity.ok(productRepository.findAll());
	}
	//get product by id
	public ResponseEntity<Product> getProduct(long id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent())
		{
			return new ResponseEntity<Product>(product.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Product>(new Product(),HttpStatus.NO_CONTENT);
		}
	}
	
	
	//create new product
	public ResponseEntity<Product> create(Product product) {
		// TODO Auto-generated method stub
		try {
			Product savedProduct = productRepository.save(product);
			return new ResponseEntity<Product>(savedProduct, HttpStatus.CREATED);
		}catch(Exception e){
			return new ResponseEntity<Product>(new Product(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//update product
	public ResponseEntity<Product> update(long id,Product product) {
		// TODO Auto-generated method stub
		Optional<Product> optionalProduct = productRepository.findById(id);
		if(optionalProduct.isPresent()) {
			try {
				Product existingProduct = optionalProduct.get();
				existingProduct.setName(product.getName());
				existingProduct.setPrice(product.getPrice());
				existingProduct.setStock(product.getStock());
				
				Product savedProduct = productRepository.save(existingProduct);
				
				return new ResponseEntity<Product>(savedProduct,HttpStatus.OK);
			}
			catch(Exception e) {
				return new ResponseEntity<Product>(new Product(),HttpStatus.NO_CONTENT);
			}
		}
		return new ResponseEntity<Product>(new Product(),HttpStatus.NO_CONTENT);
	}
	
	//delete product
	public ResponseEntity<Product> delete(long id) {
		// TODO Auto-generated method stub
		Optional <Product> optionalProduct = productRepository.findById(id);
		if(optionalProduct.isPresent() ) {
			try {
				productRepository.delete(optionalProduct.get());
				return new ResponseEntity<Product>(optionalProduct.get(),HttpStatus.ACCEPTED);
			}
			catch(Exception e) {
				return new ResponseEntity<Product>(new Product(),HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<Product>(new Product(),HttpStatus.NO_CONTENT);
	}
	

}
