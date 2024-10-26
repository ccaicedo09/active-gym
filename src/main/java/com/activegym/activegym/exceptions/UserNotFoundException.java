package com.activegym.activegym.exceptions;

/**
 * Exception thrown when an user is not found.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
}
