package com.activegym.activegym.model.Users;


import com.activegym.activegym.model.Roles.Role;
import com.activegym.activegym.model.Users.auxiliary.BloodRh;
import com.activegym.activegym.model.Users.auxiliary.BloodType;
import com.activegym.activegym.model.Users.auxiliary.Eps;
import com.activegym.activegym.model.Users.auxiliary.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String document;
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

    // User Roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
