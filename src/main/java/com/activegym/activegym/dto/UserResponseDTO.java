package com.activegym.activegym.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

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
    private Set<RoleDTO> roles;
}
