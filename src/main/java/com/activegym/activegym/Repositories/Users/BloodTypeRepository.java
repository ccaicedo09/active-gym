package com.activegym.activegym.Repositories.Users;

import com.activegym.activegym.Entities.Users.BloodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodTypeRepository extends JpaRepository<BloodType, Integer> {
    Optional<BloodType> findByBloodTypeName(String bloodTypeName);
}
