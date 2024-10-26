package com.activegym.activegym.repository.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.BloodRh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link BloodRh} entities.
 * Provides standard CRUD operations and custom queries related to blood RH factors.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Repository
public interface BloodRhRepository extends JpaRepository<BloodRh, Integer> {

    /**
     * Finds a blood RH factor by its value.
     *
     * @param bloodRh the value of the blood RH factor to search for.
     * @return an {@link Optional} containing the blood RH factor if found, or an empty Optional otherwise.
     */
    Optional<BloodRh> findByBloodRh(String bloodRh);
}
