package com.activegym.activegym.exceptions;

/**
 * Exception thrown when a blood type is not found.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
public class BloodTypeNotFoundException extends RuntimeException {
    public BloodTypeNotFoundException(String message) {
        super(message);
    }
}
