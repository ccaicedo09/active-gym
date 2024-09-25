package com.activegym.activegym.Entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "memberships")
@RequiredArgsConstructor
public class Membership {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "user_document", referencedColumnName = "document")
    private User userDocument;

    @ManyToOne
    @JoinColumn (name = "membership_type_id", referencedColumnName = "id")
    private MembershipType membershipType;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate saleDate;

    @ManyToOne
    @JoinColumn (name = "status_id" , referencedColumnName = "id")
    private MembershipStatus membershipStatus;

    @ManyToOne
    @JoinColumn(name = "sold_by", referencedColumnName = "id")
    private User soldBy;

}
