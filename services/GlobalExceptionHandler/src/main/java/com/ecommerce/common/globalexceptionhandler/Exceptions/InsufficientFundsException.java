package com.ecommerce.common.globalexceptionhandler.Exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String s) {
        super(s);
    }
}
