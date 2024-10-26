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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a user in the system.
 * This entity implements the UserDetails interface for Spring Security integration.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@Entity
@Table(name = "users")
@RequiredArgsConstructor
public class User implements UserDetails {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The document number of the user, unique and not null.
     * This document is used for most operations related to the users, including identification and access control.
     */
    @Column(unique = true, nullable = false)
    private String document;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The phone number of the user.
     */
    private String phone;

    /**
     * The email of the user, unique and not null because it is the field used for usernames.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * The password of the user, is encrypted in the database.
     */
    private String password;

    /**
     * The date of birth of the user in the format YYYY-MM-DD.
     */
    private LocalDate dateOfBirth;

    /**
     * Height and weight of the user. Will be used in further versions for "ENTRENADOR" role usage.
     */
    private Integer weight;
    private Integer height;

    /**
     * Gender of the user. It is a foreign key to the Gender table. Receives the id of the gender and when doing requests, the gender names are used. Also, this is a collection of pre-set genders, so neither more genders can be added in the future nor the existing ones can be deleted.
     */
    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    /**
     * The EPS of the user (Health Promotion Entity). It is a foreign key to the EPS table. Receives the id of the EPS and when doing requests, the EPS names are used.
     */
    @ManyToOne
    @JoinColumn(name = "eps_id", referencedColumnName = "id")
    private Eps eps;

    /**
     * The blood type of the user. It is a foreign key to the BloodType table. Receives the id of the blood type and when doing requests, the blood type names are used. Also, this is a collection of pre-set blood types, so neither more blood types can be added in the future nor the existing ones can be deleted.
     */
    @ManyToOne
    @JoinColumn(name = "blood_type_id", referencedColumnName = "id")
    private BloodType bloodType;

    /**
     * The blood Rh of the user. It is a foreign key to the BloodRh table. Receives the id of the blood Rh and when doing requests, the blood Rh names are used. Also, this is a collection of pre-set blood Rh, so neither more blood Rh can be added in the future nor the existing ones can be deleted.
     */
    @ManyToOne
    @JoinColumn(name = "blood_rh_id", referencedColumnName = "id")
    private BloodRh bloodRh;

    /**
     * The profile picture of the user. It is a URL to the image.
     */
    private String profilePicture;

    /**
     * The age of the user. It is calculated by the {@link com.activegym.activegym.util.AgeCalculator} class.
     */
    private int age;

    /**
     * The roles assigned to the user. It is a many-to-many relationship with the Role table, so an user can have multiple roles.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /**
     * Returns the authorities granted to the user.
     *
     * @return a collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());
    }

    /**
     * Returns the username of the user, which is the email for this application.
     *
     * @return the user's email.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account is expired.
     *
     * @return true always because this application does not have an expiration policy yet.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked.
     *
     * @return true always because this application does not have a lock policy yet.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials are expired.
     *
     * @return true always because this application does not have an expiration policy yet.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return true always because this application does not have a lock policy yet.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
