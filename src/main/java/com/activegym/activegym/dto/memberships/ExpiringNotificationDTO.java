package com.activegym.activegym.dto.memberships;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ExpiringNotificationDTO {
    private String userNames;
    private String userDocument;
    private String userPhone;
    private String membershipTypeName;
    private LocalDate endDate;
}
