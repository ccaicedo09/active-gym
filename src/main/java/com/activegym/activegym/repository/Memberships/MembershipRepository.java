package com.activegym.activegym.repository.Memberships;

import com.activegym.activegym.model.Memberships.Membership;
import com.activegym.activegym.model.Memberships.MembershipStatus;
import com.activegym.activegym.model.Users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Page<Membership> findAll(Pageable pageable);

    List<Membership> findAllByUserIdOrderByEndDateDesc(User user);

    List<Membership> findByMembershipStatus(MembershipStatus membershipStatus);

}
