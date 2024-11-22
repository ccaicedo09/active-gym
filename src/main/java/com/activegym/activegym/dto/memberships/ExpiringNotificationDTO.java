package com.activegym.activegym.dto.memberships;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * ExpiringNotificationDTO is a Data Transfer Object that contains the necessary information
 * for returning a membership that is about to expire.
 * @author Carlos Esteban Castro Caicedo
 * @since v1.2
 */
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
