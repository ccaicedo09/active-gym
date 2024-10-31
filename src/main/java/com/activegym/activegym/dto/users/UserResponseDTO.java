package com.activegym.activegym.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "UserResponseDTO", description = "Data Transfer Object used to represent user information in API responses")
public class UserResponseDTO {

    @Schema(name = "id", description = "Unique identifier for the user", required = true)
    private Long id;

    @Schema(name = "document", description = "User's document number", required = true)
    private String document;

    @Schema(name = "firstName", description = "User's first name", required = true)
    private String firstName;

    @Schema(name = "lastName", description = "User's last name", required = true)
    private String lastName;

    @Schema(name = "phone", description = "User's phone number", required = true)
    private String phone;

    @Schema(name = "email", description = "User's email address", required = true)
    private String email;

    @Schema(name = "dateOfBirth", description = "User's date of birth", required = true)
    private LocalDate dateOfBirth;

    @Schema(name = "genderName", description = "User's gender", required = true)
    private String genderName;

    @Schema(name = "epsName", description = "User's EPS (healthcare provider) name", required = true)
    private String epsName;

    @Schema(name = "bloodTypeName", description = "User's blood type", required = true)
    private String bloodTypeName;

    @Schema(name = "bloodRhName", description = "User's Rh factor", required = true)
    private String bloodRhName;

    @Schema(name = "age", description = "User's age", required = true)
    private int age;

    @Schema(name = "roles", description = "Set of roles assigned to the user", required = true)
    private Set<String> roles;
}
