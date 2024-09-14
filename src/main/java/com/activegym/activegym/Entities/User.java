package com.activegym.activegym.Entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "eps_id", referencedColumnName = "id")
    private Eps eps;

    @ManyToOne
    @JoinColumn(name = "blood_type_id", referencedColumnName = "id")
    private BloodType bloodType;

    @ManyToOne
    @JoinColumn(name = "blood_rh_id", referencedColumnName = "id")
    private BloodRh bloodRh;

    private String profilePicture;
    private int age; // Calculated by Service
}
