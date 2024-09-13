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
    private Integer genderId;
    private Integer epsId;
    private Integer bloodTypeId;
    private Integer bloodRhId;
}
