package com.activegym.activegym.repository.Users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.activegym.activegym.model.Users.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * Provides methods for standard CRUD operations and custom queries
 * related to users and their roles.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a paginated list of all users.
     *
     * @param pageable the pagination information (page number, size, sorting).
     * @return a {@link Page} containing users based on the pagination criteria.
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Retrieves users who have only the 'MIEMBRO' role assigned and no other roles.
     * Uses a JPQL query to perform a join with the roles and filter the results.
     *
     * @param pageable the pagination information.
     * @return a {@link Page} containing users with only the 'MIEMBRO' role.
     */
    @Query("SELECT u FROM User u JOIN u.roles r " +
            "WHERE r.roleName = 'MIEMBRO' AND SIZE(u.roles) = 1")
    Page<User> findUsersWithOnlyMemberRole(Pageable pageable);

    /**
     * Finds a user by their document number.
     *
     * @param document the document number to search for.
     * @return an {@link Optional} containing the user, if found.
     */
    Optional<User> findByDocument(String document);

    /**
     * Retrieves a list of users based on the provided role names.
     * Uses a JPQL query to find users whose roles match any of the given role names.
     *
     * @param roles a list of role names to search for.
     * @return a {@link List} of users with any of the specified roles.
     */
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName IN (:roles)")
    List<User> findByRolesIn(@Param("roles") List<String> roles);

    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for.
     * @return an {@link Optional} containing the user, if found.
     */
    Optional<User> findByEmail(String email);

}
