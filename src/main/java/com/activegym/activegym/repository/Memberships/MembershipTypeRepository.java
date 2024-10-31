package com.activegym.activegym.repository.Memberships;

import com.activegym.activegym.model.Memberships.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link MembershipType} entities.
 * Provides standard CRUD operations and custom queries related to membership types.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Repository
public interface MembershipTypeRepository extends JpaRepository<MembershipType, Long> {

    /**
     * Finds a membership type by its name.
     *
     * @param membershipTypeName the name of the membership type to search for.
     * @return an {@link Optional} containing the membership type if found, or an empty Optional otherwise.
     */
    Optional<MembershipType> findByName(String membershipTypeName);
}
