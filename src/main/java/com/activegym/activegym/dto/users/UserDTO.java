package com.activegym.activegym.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object for user information.
 * This DTO is used for transferring the necessary fields when creating an user.
 * Does not include the password, distinguishing it from {@link AdminDTO}. This happens because when
 * a new user is created by a staff, the password is by default the document. Also, make sure
 * that anytime a user is created, the client app must firstly fetch the auxiliary fields and give
 * them as the only options for the user to choose from.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@Schema(description = "Data transfer object for user information")
public class UserDTO {

    @Schema(description = "Unique document identifier of the user", example = "12345678", required = true)
    private String document;

    @Schema(description = "First name of the user", example = "John", required = true)
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe", required = true)
    private String lastName;

    @Schema(description = "Phone number of the user", example = "+1234567890", required = true)
    private String phone;

    @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
    private String email;

    @Schema(description = "Date of birth of the user", example = "1990-01-01", required = true)
    private LocalDate dateOfBirth;

    @Schema(description = "Name of the user's gender", example = "Masculino", required = true)
    private String genderName;

    @Schema(description = "Name of the user's EPS (health insurance)", example = "Sanitas", required = true)
    private String epsName;

    @Schema(description = "Blood type of the user", example = "O", required = true)
    private String bloodTypeName;

    @Schema(description = "Rh factor of the user's blood", example = "+", required = true)
    private String bloodRhName;
}
