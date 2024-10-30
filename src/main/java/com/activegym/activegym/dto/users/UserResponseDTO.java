package com.activegym.activegym.dto.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/**
 * Data Transfer Object for user response data.
 * This DTO is used to represent the user information returned in API responses,
 * including basic user details, the user's age and roles.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String document;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private LocalDate dateOfBirth;
    private String genderName;
    private String epsName;
    private String bloodTypeName;
    private String bloodRhName;
    private int age;
    private Set<String> roles;
}
