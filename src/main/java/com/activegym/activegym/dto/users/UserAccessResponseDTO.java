package com.activegym.activegym.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Response Data Transfer Object returned when a user accesses the gym.
 * @since v1.3
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@AllArgsConstructor
public class UserAccessResponseDTO {
    private Long id;
    private String accessDateTime;
    private Boolean success;
    private String userDocument;
}
