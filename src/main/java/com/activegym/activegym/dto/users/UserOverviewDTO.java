package com.activegym.activegym.dto.users;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Response Data Transfer Object for the user overview, it is used to show the basic user information in profiles and
 * access responses.
 * @since v1.3
 * @author Carlos Esteban Castro Caicedo
 */
@Data
@Builder
public class UserOverviewDTO {
    private String name;
    private List<String> roles;
    private String profilePicture;
    private boolean hasMembership;
    private int daysLeft;
}
