package com.activegym.activegym.Repositories;

import com.activegym.activegym.Entities.BloodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodTypeRepository extends JpaRepository<BloodType, Integer> {
    Optional<BloodType> findByBloodTypeName(String bloodTypeName);
}
