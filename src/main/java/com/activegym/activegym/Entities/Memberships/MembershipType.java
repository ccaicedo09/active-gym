package com.activegym.activegym.Entities.Memberships;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "membership_types")
@RequiredArgsConstructor
public class MembershipType {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String duration;
    private String description;
    private boolean isTransferable;
    private boolean isFreezable;

}
