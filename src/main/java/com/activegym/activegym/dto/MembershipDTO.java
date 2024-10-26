package com.activegym.activegym.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Membership Data Transfer Object. This DTO is used as the expected body for the creation of a new
 * user's membership.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
public class MembershipDTO {
    private String userDocument;
    private String membershipType;
    private LocalDate startDate;
}
