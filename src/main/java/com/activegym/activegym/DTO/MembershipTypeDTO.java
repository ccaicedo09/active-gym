package com.activegym.activegym.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembershipTypeDTO {
    private String name;
    private Double price;
    private String duration;
    private String description;
    private boolean isTransferable;
    private boolean isFreezable;
}
