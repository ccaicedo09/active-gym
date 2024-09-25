package com.activegym.activegym.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.activegym.activegym.Entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDocument(String document);
}
