package com.activegym.activegym.Repositories;

import com.activegym.activegym.Entities.BloodRh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodRhRepository extends JpaRepository<BloodRh, Integer> {
    Optional<BloodRh> findByBloodRh(String bloodRh);
}
