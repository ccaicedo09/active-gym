package com.activegym.activegym.Repositories;

import com.activegym.activegym.Entities.BloodType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BloodTypeRepository extends CrudRepository<BloodType, Integer> {
    Optional<BloodType> findByBloodTypeName(String bloodTypeName);
}
