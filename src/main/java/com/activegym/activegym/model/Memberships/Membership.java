package com.activegym.activegym.model.Memberships;


import com.activegym.activegym.model.Users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents a membership in the system.
 * This entity stores information about the user's membership, including its type, status,
 * and duration. It also tracks when the membership was sold and by whom.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@Entity
@Table(name = "memberships")
@RequiredArgsConstructor
public class Membership {

    /**
     * The unique identifier for the membership.
     * This ID is automatically generated and serves as the primary key for the membership entity.
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user associated with this membership.
     * This field establishes a many-to-one relationship with the {@link User} entity.
     */
    @ManyToOne
    @JoinColumn (name = "user_id", referencedColumnName = "id")
    private User userId;

    /**
     * The type of membership.
     * This field establishes a many-to-one relationship with the {@link MembershipType} entity,
     * specifying the characteristics and duration of the membership.
     */
    @ManyToOne
    @JoinColumn (name = "membership_type_id", referencedColumnName = "id")
    private MembershipType membershipType;

    /**
     * The start date of the membership.
     * This field indicates when the membership becomes active.
     */
    private LocalDate startDate;

    /**
     * The end date of the membership.
     * This field indicates when the membership expires.
     */
    private LocalDate endDate;

    /**
     * The sale date of the membership.
     * This field records the exact date the membership was purchased.
     */
    private LocalDate saleDate;

    /**
     * The status of the membership.
     * This field establishes a many-to-one relationship with the {@link MembershipStatus} entity,
     * indicating if the membership is active, inactive, or frozen.
     */
    @ManyToOne
    @JoinColumn (name = "status_id" , referencedColumnName = "id")
    private MembershipStatus membershipStatus;

    /**
     * The user who sold the membership.
     * This field establishes a many-to-one relationship with the {@link User} entity,
     * representing the staff member responsible for the sale.
     */
    @ManyToOne
    @JoinColumn(name = "sold_by", referencedColumnName = "id")
    private User soldBy;
}
