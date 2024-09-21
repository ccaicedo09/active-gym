package com.activegym.activegym.Repositories;

import com.activegym.activegym.Entities.Eps;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EpsRepository extends CrudRepository<Eps, Integer> {
    Optional<Eps> findByEpsName(String epsName);
}
