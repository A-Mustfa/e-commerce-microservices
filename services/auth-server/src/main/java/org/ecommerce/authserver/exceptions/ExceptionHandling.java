package org.ecommerce.authserver.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.authserver.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
        return handle(HttpStatus.NOT_FOUND, request, ex);
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
