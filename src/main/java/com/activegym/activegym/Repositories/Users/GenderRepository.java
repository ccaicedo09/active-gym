package com.activegym.activegym.Repositories.Users;

import com.activegym.activegym.Entities.Users.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    Optional<Gender> findByGenderName(String genderName);
}
