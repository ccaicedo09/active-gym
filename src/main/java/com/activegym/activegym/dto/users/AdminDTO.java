package com.activegym.activegym.dto.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object for creating an administrator.
 * This DTO includes the necessary information for administrator creation
 * and differs from {@link UserDTO} in that it requires a password for the new administrator account.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
public class AdminDTO {
    private String document;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String genderName;
    private String epsName;
    private String bloodTypeName;
    private String bloodRhName;
}
