package com.activegym.activegym.dto.memberships;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object required for transferring a membership to another user. It is the expected body
 * for the transfer of a user's membership.
 * @since v1.1
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@AllArgsConstructor
public class MembershipTransferDTO {
    private Long membershipId;
    private String newUserDocument;
}
