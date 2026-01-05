package org.mindtocode.ecommercebackend.exceptions;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(String message) {
        super(message);
    }

    public ProductOutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductOutOfStockException(Throwable cause) {
        super(cause);
    }
}
