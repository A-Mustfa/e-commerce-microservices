package org.ecommerce.ecommerce_service.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String s) {
        super(s);
    }
}
