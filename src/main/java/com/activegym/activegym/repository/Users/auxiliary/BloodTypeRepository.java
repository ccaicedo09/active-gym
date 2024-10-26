package com.activegym.activegym.repository.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.BloodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link BloodType} entities.
 * Provides standard CRUD operations related to blood types.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Repository
public interface BloodTypeRepository extends JpaRepository<BloodType, Integer> {
    /**
     * Finds a blood type by its name.
     *
     * @param bloodTypeName the name of the blood type to search for.
     * @return an {@link Optional} containing the blood type if found, or an empty Optional otherwise.
     */
    Optional<BloodType> findByBloodTypeName(String bloodTypeName);
}
