package com.activegym.activegym.repository.Memberships;

import com.activegym.activegym.model.Memberships.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipTypeRepository extends JpaRepository<MembershipType, Long> {
    Optional<MembershipType> findByName(String membershipTypeName);
}
