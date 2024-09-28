package com.activegym.activegym.repository.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.BloodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodTypeRepository extends JpaRepository<BloodType, Integer> {
    Optional<BloodType> findByBloodTypeName(String bloodTypeName);
}
