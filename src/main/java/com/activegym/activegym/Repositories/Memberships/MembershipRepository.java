package com.activegym.activegym.Repositories.Memberships;

import com.activegym.activegym.Entities.Memberships.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
