package com.activegym.activegym.exceptions;

/**
 * Exception thrown when a membership type is not found.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
public class MembershipTypeNotFoundException extends RuntimeException {
    public MembershipTypeNotFoundException(String message) {
        super(message);
    }
}
