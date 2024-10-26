package com.activegym.activegym.repository.Memberships;

import com.activegym.activegym.model.Memberships.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link MembershipStatus} entities.
 * Provides standard CRUD operations and custom queries related to membership statuses.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Repository
public interface MembershipStatusRepository extends JpaRepository<MembershipStatus, Long> {

    /**
     * Finds a membership status by its status.
     *
     * @param description the status of the membership to search for.
     * @return an {@link Optional} containing the membership status if found, or an empty Optional otherwise.
     */
    Optional<MembershipStatus> findByDescription(String description);
}
