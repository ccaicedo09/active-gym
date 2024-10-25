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

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r " +
            "WHERE r.roleName = 'MIEMBRO' AND SIZE(u.roles) = 1")
    Page<User> findUsersWithOnlyMemberRole(Pageable pageable);

    Optional<User> findByDocument(String document);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName IN (:roles)")
    List<User> findByRolesIn(@Param("roles") List<String> roles);

    Optional<User> findByEmail(String email);

}
