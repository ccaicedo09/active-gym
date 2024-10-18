package com.activegym.activegym.repository.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import com.activegym.activegym.model.Users.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDocument(String document);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
