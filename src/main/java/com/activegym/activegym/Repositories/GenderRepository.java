package com.activegym.activegym.Repositories;

import com.activegym.activegym.Entities.Gender;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GenderRepository extends CrudRepository<Gender, Integer> {
    Optional<Gender> findByGenderName(String genderName);
}
