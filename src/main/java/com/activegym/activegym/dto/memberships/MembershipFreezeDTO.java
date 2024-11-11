package com.activegym.activegym.dto.memberships;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MembershipFreezeDTO {
    private Long membershipId;
    private int days;
}
