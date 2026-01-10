package org.mindtocode.ecommercebackend.exceptions;

import org.mindtocode.ecommercebackend.model.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ProductNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
                ErrorResponse errorResponse = new ErrorResponse(
                                ex.getMessage(),
                                "Product Not Found",
                                HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        @ExceptionHandler(ProductOutOfStockException.class)
        public ResponseEntity<ErrorResponse> handleProductOutOfStockException(ProductOutOfStockException ex) {
                ErrorResponse errorResponse = new ErrorResponse(
                                ex.getMessage(),
                                "Product Out Of Stock",
                                HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
                Map<String, String> errors = new HashMap<>();
                ex.getBindingResult().getFieldErrors()
                                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

                String message = "Validation failed: " + errors.toString();
                ErrorResponse errorResponse = new ErrorResponse(
                                message,
                                "Validation Error",
                                HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
                String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s",
                                ex.getValue(), ex.getName(),
                                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
                ErrorResponse errorResponse = new ErrorResponse(
                                message,
                                "Invalid Parameter Type",
                                HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
                ErrorResponse errorResponse = new ErrorResponse(
                                ex.getMessage(),
                                "Invalid Argument",
                                HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
                String message = ex.getMessage();
                String errorMessage = "A record with this information already exists";

                // Check if it's a unique constraint violation (PostgreSQL specific)
                if (message != null) {
                        String lowerMessage = message.toLowerCase();
                        if (lowerMessage.contains("unique") || lowerMessage.contains("duplicate")
                                        || lowerMessage.contains("uk_username") || lowerMessage.contains("username")) {
                                errorMessage = "Username already exists";
                        }
                }

                ErrorResponse errorResponse = new ErrorResponse(
                                errorMessage,
                                "Data Integrity Violation",
                                HttpStatus.CONFLICT.value());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
                ErrorResponse errorResponse = new ErrorResponse(
                                "An unexpected error occurred: " + ex.getMessage(),
                                "Internal Server Error",
                                HttpStatus.INTERNAL_SERVER_ERROR.value());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

        @ExceptionHandler(AuthorizationDeniedException.class)
        public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthorizationDeniedException ex) {
                ErrorResponse errorResponse = new ErrorResponse(
                                ex.getMessage(),
                                "Authentication Error",
                                HttpStatus.UNAUTHORIZED.value());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
}
