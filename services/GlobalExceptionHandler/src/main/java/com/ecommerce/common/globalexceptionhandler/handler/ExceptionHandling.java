package com.ecommerce.common.globalexceptionhandler.handler;

import com.ecommerce.common.globalexceptionhandler.Exceptions.*;
import com.ecommerce.common.globalexceptionhandler.Exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import com.ecommerce.common.globalexceptionhandler.Exceptions.*;
import com.ecommerce.common.globalexceptionhandler.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        return handle(HttpStatus.NOT_FOUND, request, ex);
    }

//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleUserNameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
//        return handle(HttpStatus.NOT_FOUND, request, ex);
//    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleFundsException(InsufficientFundsException ex, HttpServletRequest request) {
        return handle(HttpStatus.CONFLICT, request, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        (error) -> {
                            errors.put( ((FieldError) error).getField(),error.getDefaultMessage());
                        });
        return handle(HttpStatus.BAD_REQUEST, request, ex, errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, HttpServletRequest request) {
        return handle(HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExists ex, HttpServletRequest request) {
        return handle(HttpStatus.CONFLICT, request, ex);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException ex, HttpServletRequest request) {
        return handle(HttpStatus.NOT_FOUND, request, ex);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException ex, HttpServletRequest request) {
        return handle(HttpStatus.NOT_FOUND, request, ex);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCartItemNotFoundException(CartItemNotFoundException ex, HttpServletRequest request) {
        return handle(HttpStatus.NOT_FOUND, request, ex);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCartNotFoundException(CartNotFoundException ex, HttpServletRequest request) {
        return handle(HttpStatus.NOT_FOUND, request, ex);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException ex, HttpServletRequest request) {
        return handle(HttpStatus.CONFLICT, request, ex);
    }

    @ExceptionHandler(ItemAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleItemAlreadyExistsException(ItemAlreadyExists ex, HttpServletRequest request) {
        return handle(HttpStatus.CONFLICT, request, ex);
    }

    @ExceptionHandler(OrderCannotBeCanceledException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(OrderCannotBeCanceledException ex, HttpServletRequest request) {
        return handle(HttpStatus.CONFLICT, request, ex);
    }

    public ResponseEntity<ErrorResponse> handle(HttpStatus status, HttpServletRequest request, Exception ex, Map<String, String> errors) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                errors != null && !errors.isEmpty() ? "Validation failed" : status.getReasonPhrase(),
                ex.getMessage(),
                request.getPathInfo(),
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
