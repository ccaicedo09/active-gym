package com.activegym.activegym.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembershipTypeDTO {
    private String name;
    private Double price;
    private Integer duration;
    private String description;
    private boolean isTransferable;
    private boolean isFreezable;
}
