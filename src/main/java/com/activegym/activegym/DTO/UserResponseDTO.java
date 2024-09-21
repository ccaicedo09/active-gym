package com.activegym.activegym.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
}
