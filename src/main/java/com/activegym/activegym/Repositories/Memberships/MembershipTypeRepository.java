package com.activegym.activegym.Repositories.Memberships;

import com.activegym.activegym.Entities.Memberships.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipTypeRepository extends JpaRepository<MembershipType, Long> {
    Optional<MembershipType> findByName(String membershipTypeName);
}
