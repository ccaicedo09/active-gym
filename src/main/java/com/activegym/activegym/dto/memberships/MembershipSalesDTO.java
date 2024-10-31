package com.activegym.activegym.dto.memberships;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MembershipSalesDTO {
    private String membershipTypeName;
    private Long count;
}
