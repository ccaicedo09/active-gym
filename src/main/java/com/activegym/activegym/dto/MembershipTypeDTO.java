package com.activegym.activegym.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembershipTypeDTO {
    private String name;
    private Double price;
    private Integer duration;
    private String description;

    @JsonProperty("isTransferable")
    private boolean isTransferable;

    @JsonProperty("isFreezable")
    private boolean isFreezable;
}
