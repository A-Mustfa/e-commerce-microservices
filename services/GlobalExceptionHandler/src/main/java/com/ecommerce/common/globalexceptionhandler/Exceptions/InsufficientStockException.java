package com.ecommerce.common.globalexceptionhandler.Exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String msg) {
        super(msg);
    }
}
