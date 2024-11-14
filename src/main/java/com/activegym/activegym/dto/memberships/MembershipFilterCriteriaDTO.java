package com.activegym.activegym.dto.memberships;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.time.LocalDate;

/**
 * Data Transfer Object for filtering memberships.
 * This DTO includes the required params for filtering memberships through the respective endpoints
 * that need memberships' filtering.
 *
 * @since v1.2
 * @author Carlos Esteban Castro Caicedo
 */
@Data
@Builder
public class MembershipFilterCriteriaDTO {
    private String userDocument;
    private String membershipType;
    private String membershipStatus;

    @JsonProperty("frozen")
    private Boolean frozen;

    @JsonProperty("transferred")
    private Boolean transferred;
}
