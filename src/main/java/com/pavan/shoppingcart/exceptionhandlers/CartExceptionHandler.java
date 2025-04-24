package com.pavan.shoppingcart.exceptionhandlers;


@SuppressWarnings("serial")
public class CartExceptionHandler extends RuntimeException {
	public CartExceptionHandler(String message) {
		super(message);
	}
}
