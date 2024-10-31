package com.activegym.activegym.exceptions;

/**
 * Exception thrown when a gender is not found.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
public class GenderNotFoundException extends RuntimeException{
    public GenderNotFoundException(String message) {
        super(message);
    }
}
