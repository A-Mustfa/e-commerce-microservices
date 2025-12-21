package org.ecommerce.ecommerce_service.exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String msg) {
        super(msg);
    }
}
