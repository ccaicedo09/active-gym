package com.activegym.activegym.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MembershipResponseDTO {
    private Long id;
    private String userDocument;
    private String membershipType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate saleDate;
    private String membershipStatus;
    private String soldByDocument;
}
