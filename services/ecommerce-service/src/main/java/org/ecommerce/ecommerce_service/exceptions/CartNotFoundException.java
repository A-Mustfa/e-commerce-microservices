package org.ecommerce.ecommerce_service.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String s) {
        super(s);
    }
}
