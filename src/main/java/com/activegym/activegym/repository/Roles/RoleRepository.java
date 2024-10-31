package com.activegym.activegym.repository.Roles;

import com.activegym.activegym.model.Roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Role} entities.
 * Provides standard CRUD operations and custom queries for roles.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Finds a role by its name.
     *
     * @param roleName the name of the role to search for.
     * @return an {@link Optional} containing the role if found, or an empty Optional otherwise.
     */
    Optional<Role> findByRoleName(String roleName);
}
