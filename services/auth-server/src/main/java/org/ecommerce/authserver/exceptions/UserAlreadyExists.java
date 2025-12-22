package org.ecommerce.authserver.exceptions;

import jakarta.validation.constraints.Email;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(@Email String s) {
        super(s);
    }
}
