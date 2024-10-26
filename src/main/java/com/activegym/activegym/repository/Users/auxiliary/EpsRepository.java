package com.activegym.activegym.repository.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.Eps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Eps} entities.
 * Provides standard CRUD operations related to EPS.
 * EPS stands for "Entidad Promotora de Salud" in Spanish, which translates to "Health Promoting Entity".
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Repository
public interface EpsRepository extends JpaRepository<Eps, Integer> {

    /**
     * Finds an EPS by its name.
     *
     * @param epsName the name of the EPS to search for.
     * @return an {@link Optional} containing the EPS entity if found, or an empty Optional otherwise.
     */
    Optional<Eps> findByEpsName(String epsName);
}
