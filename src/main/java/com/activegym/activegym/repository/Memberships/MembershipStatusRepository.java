package com.activegym.activegym.repository.Memberships;

import com.activegym.activegym.model.Memberships.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipStatusRepository extends JpaRepository<MembershipStatus, Long> {
    Optional<MembershipStatus> findByDescription(String description);
}
