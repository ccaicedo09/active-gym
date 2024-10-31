package com.activegym.activegym.exceptions;

/**
 * Exception thrown when an EPS is not found.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
public class EpsNotFoundException extends RuntimeException {
    public EpsNotFoundException(String message) {
        super(message);
    }
}
