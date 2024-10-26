package com.activegym.activegym.repository.Memberships;

import com.activegym.activegym.model.Memberships.Membership;
import com.activegym.activegym.model.Memberships.MembershipStatus;
import com.activegym.activegym.model.Users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Membership} entities.
 * Provides standard CRUD operations and custom queries related to memberships.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    /**
     * Retrieves a paginated list of all memberships.
     *
     * @param pageable the pagination information.
     * @return a {@link Page} containing the memberships.
     */
    Page<Membership> findAll(Pageable pageable);

    /**
     * Retrieves all memberships for a specific user, ordered by the end date in descending order.
     *
     * @param user the {@link User} whose memberships are to be retrieved.
     * @return a list of memberships associated with the given user, sorted by end date.
     */
    List<Membership> findAllByUserIdOrderByEndDateDesc(User user);

    /**
     * Retrieves all memberships with a specific status.
     *
     * @param membershipStatus the {@link MembershipStatus} to filter memberships by.
     * @return a list of memberships that match the given status.
     */
    List<Membership> findByMembershipStatus(MembershipStatus membershipStatus);

}
