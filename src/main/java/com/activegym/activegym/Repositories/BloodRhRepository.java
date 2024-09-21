package com.activegym.activegym.Repositories;

import com.activegym.activegym.Entities.BloodRh;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BloodRhRepository extends CrudRepository<BloodRh, Integer> {
    Optional<BloodRh> findByBloodRh(String bloodRh);
}
