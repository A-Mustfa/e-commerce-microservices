package com.ecommerce.common.globalexceptionhandler.Exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
