package org.ecommerce.payment.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.payment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RestControllerAdvice
public class Handling {

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleFundsException(InsufficientFundsException ex, HttpServletRequest request) {
        return handle(HttpStatus.CONFLICT, request, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, HttpServletRequest request) {
        return handle(HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        (error) -> {
                            errors.put( ((FieldError) error).getField(),error.getDefaultMessage());
                        });
        return handle(HttpStatus.BAD_REQUEST, request, ex, errors);
    }

    public ResponseEntity<ErrorResponse> handle(HttpStatus status, HttpServletRequest request, Exception ex, Map<String, String> errors) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                errors != null && !errors.isEmpty() ? "Validation failed" : status.getReasonPhrase(),
                errors == null && errors.isEmpty() ? ex.getMessage() : null ,
                request.getRequestURI(),
                errors
        );
        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

    public ResponseEntity<ErrorResponse> handle(HttpStatus status, HttpServletRequest request, Exception ex) {
        return handle(status, request, ex, null);
    }
}
