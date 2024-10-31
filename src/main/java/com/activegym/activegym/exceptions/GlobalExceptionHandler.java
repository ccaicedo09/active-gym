package com.activegym.activegym.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

/**
 * Class that handles exceptions globally.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        if (e.getMessage().contains("unique")) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body("Ya existe un recurso con los datos proporcionados");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error inesperado en el servidor");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("No tienes permisos para acceder a este recurso");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado");
    }

    @ExceptionHandler(MembershipTypeNotFoundException.class)
    public ResponseEntity<String> handleMembershipTypeNotFoundException(MembershipTypeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Tipo de membresía no encontrado");
    }

    @ExceptionHandler(MembershipStatusNotFoundException.class)
    public ResponseEntity<String> handleMembershipStatusNotFoundException(MembershipStatusNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Estado de membresía no encontrado");
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleRoleNotFoundException(RoleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Rol no encontrado");
    }

    @ExceptionHandler(BloodRhNotFoundException.class)
    public ResponseEntity<String> handleBloodRhNotFoundException(BloodRhNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Factor Rh no encontrado");
    }

    @ExceptionHandler(BloodTypeNotFoundException.class)
    public ResponseEntity<String> handleBloodTypeNotFoundException(BloodTypeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Tipo de sangre no encontrado");
    }

    @ExceptionHandler(EpsNotFoundException.class)
    public ResponseEntity<String> handleEpsNotFoundException(EpsNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("EPS no encontrada");
    }

    @ExceptionHandler(GenderNotFoundException.class)
    public ResponseEntity<String> handleGenderNotFoundException(GenderNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Género no encontrado");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Credenciales inválidas");
    }

    @ExceptionHandler(MembershipNotFoundException.class)
    public ResponseEntity<String> handleMembershipNotFoundException(MembershipNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Membresía no encontrada");
    }
}
