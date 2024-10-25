package com.activegym.activegym.model.Memberships;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "membership_types")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class MembershipType {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Integer duration;
    @Column(length = 1000)
    private String description;

    @JsonProperty("isTransferable")
    private boolean isTransferable;

    @JsonProperty("isFreezable")
    private boolean isFreezable;

    @JsonProperty("isVisible")
    @Column(name = "is_visible")
    private boolean isVisible;

}
