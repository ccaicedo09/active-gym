package com.activegym.activegym.repository.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.Eps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EpsRepository extends JpaRepository<Eps, Integer> {
    Optional<Eps> findByEpsName(String epsName);
}
