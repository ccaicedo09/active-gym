package com.activegym.activegym.dto.users;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserOverviewDTO {
    private String name;
    private List<String> roles;
    private String profilePicture;
    private boolean hasMembership;
    private int daysLeft;
}
