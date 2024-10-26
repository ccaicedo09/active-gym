package com.activegym.activegym.model.Users.auxiliary;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a Gender in the system.
 * This entity is used to define different gender types that can be assigned to a User.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@Entity
@Table(name = "genders")
public class Gender {
    /**
     * The unique identifier for a Gender.
     * This ID is automatically generated and serves as the primary key for the gender entity.
     * Also, is used as the gender foreign key in the {@link com.activegym.activegym.model.Users.User} entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the gender.
     * This field must be unique across all gender records and is used for identification purposes.
     * As there is a pre-set list of genders, no operations for editing, deleting or creating genders
     * exist, as only these types exist: Masculino, Femenino and Otro.
     */
    @Column(unique = true)
    private String genderName;
}
