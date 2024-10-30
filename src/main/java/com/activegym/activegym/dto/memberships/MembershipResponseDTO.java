package com.activegym.activegym.dto.memberships;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


/**
 * Data Transfer Object for membership response data.
 * This DTO is used to represent the details of a membership in API responses,
 * including information about the user, membership type, dates, status,
 * and who sold the membership.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
public class MembershipResponseDTO {
    private Long id;
    private String userDocument;
    private String membershipTypeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate saleDate;
    private String membershipStatus;
    private String soldByDocument;
}
