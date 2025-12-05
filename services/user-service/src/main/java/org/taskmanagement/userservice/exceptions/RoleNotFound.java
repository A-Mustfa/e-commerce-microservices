package org.taskmanagement.userservice.exceptions;

public class RoleNotFound extends RuntimeException {
    public RoleNotFound(String message) {
        super(message);
    }
}
