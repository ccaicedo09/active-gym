package com.activegym.activegym.model.Memberships;


import com.fasterxml.jackson.annotation.JsonProperty;
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
 * Represents a type of membership available in the system.
 * This entity defines various characteristics such as the name, price, duration,
 * and additional attributes like transferability and visibility.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@Entity
@Table (name = "membership_types")
@RequiredArgsConstructor
public class MembershipType {

    /**
     * The unique identifier for the membership type.
     * This ID is automatically generated and serves as the primary key for the entity.
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the membership type.
     * Example: "Basic", "Premium", or "VIP".
     */
    private String name;

    /**
     * The price of the membership.
     * This value represents the cost associated with this type of membership.
     */
    private Double price;

    /**
     * The duration of the membership in days.
     * Example: 30 for a one-month membership, 365 for an annual membership.
     */
    private Integer duration;

    /**
     * A detailed description of the membership type.
     * This field can contain up to 1000 characters, allowing for a comprehensive explanation
     * of the benefits, terms, or conditions of the membership. These features should be split by "-". For example:
     * "-Access to all facilities -Unlimited classes -Personal trainer included".
     */
    @Column(length = 1000)
    private String description;

    /**
     * Indicates whether the membership is transferable.
     * If true, the membership can be transferred to another user.
     * Mapped to the JSON property "isTransferable" for serialization purposes.
     */
    @JsonProperty("isTransferable")
    private boolean isTransferable;

    /**
     * Indicates whether the membership is freezable.
     * If true, the membership can be temporarily paused or frozen.
     * Mapped to the JSON property "isFreezable" for serialization purposes.
     */
    @JsonProperty("isFreezable")
    private boolean isFreezable;

    /**
     * Indicates whether the membership is visible to users.
     * If true, the membership is available for selection or purchase.
     * Stored in the database under the column name "is_visible".
     * Mapped to the JSON property "isVisible" for serialization purposes.
     */
    @JsonProperty("isVisible")
    @Column(name = "is_visible")
    private boolean isVisible;
}
