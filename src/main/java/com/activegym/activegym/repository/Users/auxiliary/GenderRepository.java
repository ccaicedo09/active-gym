package com.activegym.activegym.repository.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Gender} entities.
 * Provides methods for CRUD operations and custom queries related to gender.
 */
@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    /**
     * Finds a gender by its name.
     *
     * @param genderName the name of the gender to search for.
     * @return an {@link Optional} containing the gender, if found.
     */
    Optional<Gender> findByGenderName(String genderName);
}
