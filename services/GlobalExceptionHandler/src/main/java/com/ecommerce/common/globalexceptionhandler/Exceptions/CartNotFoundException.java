package com.ecommerce.common.globalexceptionhandler.Exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String s) {
        super(s);
    }
}
