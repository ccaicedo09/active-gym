package com.activegym.activegym.dto;

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
public class UserDTO {
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
}
