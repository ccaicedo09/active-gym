package com.activegym.activegym.repository.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.BloodRh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodRhRepository extends JpaRepository<BloodRh, Integer> {
    Optional<BloodRh> findByBloodRh(String bloodRh);
}
