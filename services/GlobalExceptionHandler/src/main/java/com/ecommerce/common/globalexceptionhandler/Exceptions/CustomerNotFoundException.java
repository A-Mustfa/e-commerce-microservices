package com.ecommerce.common.globalexceptionhandler.Exceptions;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String s) {
        super(s);
    }
}
