package com.activegym.activegym.model.Memberships;


import com.activegym.activegym.model.Users.User;
import jakarta.persistence.Column;
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
     * Used as the order number for the membership.
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
     * The amount paid for the membership.
     * This field stores the total amount paid for the membership to avoid discrepancies.
     * @since v1.1
     */
    private Double paidAmount;

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

    /**
     * Indicates if the membership has been transferred.
     * This field is a boolean value that specifies if the membership has been transferred to another user, if true,
     * it cannot be transferred again.
     */
    @Column(name = "transferred", nullable = false)
    private boolean transferred = false;

    // Freezing fields

    /**
     * Indicates if the membership has been frozen before.
     * This field is a boolean value that specifies if the membership has been frozen before, if true,
     * it cannot be frozen again.
     * @since v1.1
     */
    @Column(name = "frozen", nullable = false)
    private boolean frozen = false;


    /**
     * The date when the membership was frozen, if applies.
     * @since v1.1
     */
    private LocalDate freezeDate;

    /**
     * The date when the membership is supposed to be unfrozen.
     * @since v1.1
     */
    private LocalDate unfreezeDate;
}
