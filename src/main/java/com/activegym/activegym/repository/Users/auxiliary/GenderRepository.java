package com.activegym.activegym.repository.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    Optional<Gender> findByGenderName(String genderName);
}
