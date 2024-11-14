package com.activegym.activegym.dto.users;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for filtering users.
 * This DTO includes the required params for filtering users through the respective endpoints
 * that need users' filtering.
 *
 * @since v1.2
 * @author Carlos Esteban Castro Caicedo
 */
@Data
@Builder
public class UserFilterCriteriaDTO {
    private String document;
    private String name;
    private String phone;
}
