package com.ecommerce.common.globalexceptionhandler.Exceptions;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(String msg) {
        super(msg);
    }
}
