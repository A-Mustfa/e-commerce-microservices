package org.ecommerce.ecommerce_service.exceptions;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(String msg) {
        super(msg);
    }
}
