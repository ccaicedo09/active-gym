package com.activegym.activegym.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MembershipDTO {
    private String userDocument;
    private String membershipType;
    private LocalDate startDate;
}
