package com.activegym.activegym.Repositories;

import com.activegym.activegym.Entities.Eps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EpsRepository extends JpaRepository<Eps, Integer> {
    Optional<Eps> findByEpsName(String epsName);
}
