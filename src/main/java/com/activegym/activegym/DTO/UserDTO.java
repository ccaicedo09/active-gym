package com.activegym.activegym.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private LocalDate dateOfBirth;
    private int genderId;
    private String epsId;
    private String bloodTypeId;
    private String bloodRhId;
}
