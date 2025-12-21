package org.ecommerce.ecommerce_service.exceptions;

public class OrderCannotBeCanceledException extends RuntimeException {
    public OrderCannotBeCanceledException(String msg) {
        super(msg);
    }
}
