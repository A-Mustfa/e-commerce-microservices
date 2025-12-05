package org.taskmanagement.orderservice.exceptions;

public class OrderCannotBeCanceledException extends RuntimeException {
    public OrderCannotBeCanceledException(String msg) {
        super(msg);
    }
}
