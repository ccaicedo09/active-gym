package com.activegym.activegym.repository.Memberships;

import com.activegym.activegym.model.Memberships.Membership;
import com.activegym.activegym.model.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findByUserId(User user);
}
