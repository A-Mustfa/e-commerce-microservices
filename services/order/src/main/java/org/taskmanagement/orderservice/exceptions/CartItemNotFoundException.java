package org.taskmanagement.orderservice.exceptions;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(String msg) {
        super(msg);
    }
}
