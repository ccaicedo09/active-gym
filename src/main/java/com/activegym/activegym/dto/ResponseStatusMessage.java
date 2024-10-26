package com.activegym.activegym.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * Service class for holding response status messages.
 * This class encapsulates a message that can be used to convey
 * the status of an operation in API responses.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@Service
public class ResponseStatusMessage {
    private String message;
}
