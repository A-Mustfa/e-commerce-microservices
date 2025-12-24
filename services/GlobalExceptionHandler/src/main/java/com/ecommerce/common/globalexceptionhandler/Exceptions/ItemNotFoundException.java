package com.ecommerce.common.globalexceptionhandler.Exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String s) {
        super(s);
    }
}
