package com.activegym.activegym.exceptions;

/**
 * Exception thrown when a blood Rh is not found.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
public class BloodRhNotFoundException extends RuntimeException {
    public BloodRhNotFoundException(String message) {
        super(message);
    }
}
