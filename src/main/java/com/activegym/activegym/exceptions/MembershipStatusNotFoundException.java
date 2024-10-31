package com.activegym.activegym.exceptions;

/**
 * Exception thrown when a membership status is not found.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
public class MembershipStatusNotFoundException extends RuntimeException {
    public MembershipStatusNotFoundException(String message) {
        super(message);
    }
}
