package com.activegym.activegym.Repositories.Memberships;

import com.activegym.activegym.Entities.Memberships.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipStatusRepository extends JpaRepository<MembershipStatus, Long> {
    Optional<MembershipStatus> findByDescription(String description);
}
