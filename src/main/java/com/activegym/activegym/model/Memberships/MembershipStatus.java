package com.activegym.activegym.model.Memberships;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Represents the status of a membership in the system.
 * This entity defines different statuses that can be assigned to memberships,
 * such as active, inactive, or cancelled.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@Entity
@Table (name = "membership_status")
@RequiredArgsConstructor
public class MembershipStatus {

    /**
     * The unique identifier for the membership status.
     * This ID is automatically generated and serves as the primary key for the status entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the membership status.
     * This field is used to identify the status by name, it must be unique across all membership statuses.
     * As there is a predefined set of statuses ("ACTIVA", "INACTIVA", "CONGELADA"), this field is not intended to be modified.
     */
    @Column(unique = true)
    private String description;

}
