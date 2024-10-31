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
 * Represents a blood Rh factor in the system.
 * This entity is used to define different Rh factors that can be associated with users
 * for medical and health-related purposes.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@Entity
@Table(name = "blood_rh")
public class BloodRh {
    /**
     * The unique identifier for the blood Rh factor. This ID is automatically generated and serves as the primary key
     * for the blood Rh factor entity. Also, is used as the blood Rh factor foreign key in the
     * {@link com.activegym.activegym.model.Users.User} entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the blood Rh factor. This field must be unique across all blood Rh factor records and is used for
     * identification purposes. As there is a pre-set of Rh factors, no operations for editing, deleting or creating
     * exist as only these factors exist: +, -.
     */
    @Column(unique = true)
    private String bloodRh;
}
