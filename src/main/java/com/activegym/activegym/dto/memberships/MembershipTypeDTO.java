package com.activegym.activegym.dto.memberships;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for membership type information.
 * This DTO is used to transfer details related to membership types creation,
 * including their attributes such as name, price, duration, description,
 * and properties like transferability, freezability, and visibility.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
public class MembershipTypeDTO {
    private String name;
    private Double price;
    private Integer duration;
    private String description;

    @JsonProperty("isTransferable")
    private boolean isTransferable;

    @JsonProperty("isFreezable")
    private boolean isFreezable;

    @JsonProperty("isVisible")
    private boolean isVisible;
}
