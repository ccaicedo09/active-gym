package com.activegym.activegym.Repositories.Users;

import com.activegym.activegym.Entities.Users.Eps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EpsRepository extends JpaRepository<Eps, Integer> {
    Optional<Eps> findByEpsName(String epsName);
}
