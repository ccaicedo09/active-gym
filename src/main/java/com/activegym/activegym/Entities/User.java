package com.activegym.activegym.Entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
@RequiredArgsConstructor
public class User {

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private Integer weight;
    private Integer height;
    private int genderId;
    private int epsId;
    private int bloodTypeId;
    private int bloodRhId;
    private String profilePicture;
    private int age; // Calculated by Service
}
