package com.activegym.activegym.exceptions;

/**
 * Exception thrown when a role is not found.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
