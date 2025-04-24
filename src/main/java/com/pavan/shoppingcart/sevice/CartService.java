package com.pavan.shoppingcart.sevice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pavan.shoppingcart.dto.CartDTO;
import com.pavan.shoppingcart.exceptionhandlers.CartExceptionHandler;
import com.pavan.shoppingcart.models.Cart;
import com.pavan.shoppingcart.models.CartItem;
import com.pavan.shoppingcart.models.Product;
import com.pavan.shoppingcart.models.User;
import com.pavan.shoppingcart.repository.CartItemRepo;
import com.pavan.shoppingcart.repository.CartRepo;
import com.pavan.shoppingcart.repository.ProductRepo;

@Service
public class CartService {

//	@PersistenceContext
//	private EntityManager entityManager;
    private final CartItemRepo cartItemRepo;

	@Autowired
	public CartRepo cartRepo;
	@Autowired
	public ProductRepo productRepo;

    CartService(CartItemRepo cartItemRepo) {
        this.cartItemRepo = cartItemRepo;
    }
    
    //get User Cart
    @Transactional(readOnly = true)
	public ResponseEntity<CartDTO> getUserCart(User user) {
		Optional<Cart> cart = cartRepo.findByUser(user);
		if(cart.isPresent()) {
			return new ResponseEntity<CartDTO>(CartDTO.cartToDTO(cart.get()),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<CartDTO>(HttpStatus.BAD_REQUEST);
		}
	}
	
    @Transactional
	//add product to cart
	public ResponseEntity<CartDTO> addProductToCart(User user, long productId) {
		Optional<Cart> optionalCart = cartRepo.findByUser(user);
		Optional<Product> optionalProduct = productRepo.findById(productId);
		if(optionalCart.isPresent()) {
			Optional<CartItem> optionalCartItem = cartItemRepo.findByCartAndProduct(optionalCart.get(),optionalProduct.get());
			if(optionalProduct.isPresent() && optionalCartItem.isEmpty()) {
					CartItem cartItem = new CartItem();
					cartItem.setCart(optionalCart.get());
					cartItem.setProduct(optionalProduct.get());
					cartItem.setQuantity(1);
					optionalCart.get().getCartItems().add(cartItem);
					Cart savedCart = cartRepo.save(optionalCart.get());
					return new ResponseEntity<CartDTO>(CartDTO.cartToDTO(savedCart),HttpStatus.CREATED);
			}
			else if(optionalCartItem.isPresent()) {
				return new ResponseEntity<CartDTO>(CartDTO.cartToDTO(optionalCart.get()),HttpStatus.OK);
			}
			else {
				return new ResponseEntity<CartDTO>(HttpStatus.BAD_REQUEST);
			}
		}
		else{
			if(optionalCart.isEmpty() && optionalProduct.isPresent()){
				Cart cart = new Cart();
				cart.setUser(user);
				CartItem cartItem = new CartItem();
				cartItem.setCart(cart);
				cartItem.setProduct(optionalProduct.get());
				cartItem.setQuantity(1);
				cart.setCartItems(new ArrayList<CartItem>(Arrays.asList(cartItem)));
				Cart savedCart = cartRepo.save(cart);
				return new ResponseEntity<CartDTO>(CartDTO.cartToDTO(cartRepo.findById(savedCart.getId()).get()),HttpStatus.CREATED);
			}
			return new ResponseEntity<CartDTO>(HttpStatus.BAD_REQUEST);
		}
	}

    
    
    
    @Transactional
	public ResponseEntity<CartDTO> update(User user, long productId,int quantity)throws CartExceptionHandler {
    	
    	Cart cart = cartRepo.findByUser(user).orElse(null);
    	if(cart == null) {
    			cart = new Cart();
    			cart.setUser(user);
    			cart.setCartItems(new ArrayList<CartItem>());
    			cart = cartRepo.save(cart);
    	}
    	Optional <Product> optionalProduct = productRepo.findById(productId);
    	if(optionalProduct.isPresent() && quantity<=0) {
    		cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId()==productId);
    		Cart savedCart = cartRepo.save(cart);
    		return new ResponseEntity<CartDTO>(CartDTO.cartToDTO(savedCart),HttpStatus.OK);
    	}
    	else if (optionalProduct.isPresent() && quantity<=optionalProduct.get().getStock()) {
    		boolean cartItemStatus = cart.getCartItems().stream().anyMatch(cartItem -> cartItem.getProduct().equals(optionalProduct.get()));
    		Cart savedCart;
    		if(cartItemStatus) {
    			cart.getCartItems().forEach(cartItem -> {
        			if(cartItem.getProduct().getId()==productId) {
        				cartItem.setQuantity(quantity);
        			}
        		});
    		savedCart = cartRepo.save(cart);
    		}
    		else {
    			CartItem cartItem = new CartItem();
    			cartItem.setCart(cart);
    			cartItem.setProduct(optionalProduct.get());
    			cartItem.setQuantity(quantity);
    			cart.getCartItems().add(cartItem);
    			savedCart = cartRepo.save(cart);
    		}
    		return new ResponseEntity<CartDTO>(CartDTO.cartToDTO(savedCart),HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<CartDTO>(HttpStatus.BAD_REQUEST);
    	}
	}

    
    
    
    @Transactional
	public ResponseEntity<CartDTO> deleteItem(User user, long productid) throws CartExceptionHandler{
		Cart cart = cartRepo.findByUser(user).orElseThrow(()->new CartExceptionHandler("CartNotFound::Add a product to create a new cart"));
			cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId() == productid);
			Cart savedCart = cartRepo.save(cart);
			return new ResponseEntity<CartDTO> (CartDTO.cartToDTO(savedCart),HttpStatus.OK);
	}
    
    
    
    
	@Transactional
	public ResponseEntity<CartDTO> deleteAllItems(User user) throws CartExceptionHandler {
		Cart cart = cartRepo.findByUser(user).orElseThrow(()->new CartExceptionHandler("CartNotFound::Add a product to create a new cart"));
		cart.getCartItems().clear();
		cartRepo.save(cart);
		return new ResponseEntity<CartDTO>(CartDTO.cartToDTO(cart),HttpStatus.OK);
	}
}
