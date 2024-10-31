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
 * Represents a blood type in the system.
 * This entity is used to define different blood types that can be associated with users
 * for medical and health-related purposes.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@Entity
@Table(name = "blood_type")
public class BloodType {
    /**
     * The unique identifier for the blood type.
     * This ID is automatically generated and serves as the primary key for the blood type entity.
     * Also, is used as the blood type foreign key in the {@link com.activegym.activegym.model.Users.User} entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the blood type.
     * This field must be unique across all blood type records and is used for identification purposes.
     * As there is a pre-set list of blood types, no operations for editing, deleting or creating blood types
     * exist, as only these types exist: A, B, O, AB.
     */
    @Column(unique = true)
    private String bloodTypeName;
}
