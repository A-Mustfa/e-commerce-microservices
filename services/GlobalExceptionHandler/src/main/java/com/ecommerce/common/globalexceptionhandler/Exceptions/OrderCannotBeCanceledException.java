package com.ecommerce.common.globalexceptionhandler.Exceptions;

public class OrderCannotBeCanceledException extends RuntimeException {
    public OrderCannotBeCanceledException(String msg) {
        super(msg);
    }
}
