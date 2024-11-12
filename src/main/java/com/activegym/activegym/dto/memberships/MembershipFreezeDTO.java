package com.activegym.activegym.dto.memberships;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Membership Freeze Data Transfer Object. This DTO is used as the expected body for the freeze of a
 * user's membership.
 * @since v1.1
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@AllArgsConstructor
public class MembershipFreezeDTO {
    private Long membershipId;
    private int days;
}
