package com.activegym.activegym.dto.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for user counter access in the gym.
 * @author Carlos Esteban Castro Caicedo
 * @since v1.3
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessDTO {
    private String document;
}
