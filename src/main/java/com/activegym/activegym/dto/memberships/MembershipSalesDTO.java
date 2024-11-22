package com.activegym.activegym.dto.memberships;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for membership sales count.
 * @author Carlos Esteban Castro Caicedo
 * @since v1.2
 */
@Getter
@Setter
@AllArgsConstructor
public class MembershipSalesDTO {
    private String membershipTypeName;
    private Long count;
}
