package com.activegym.activegym.Repositories;

import com.activegym.activegym.Entities.Gender;
import org.springframework.data.repository.CrudRepository;

public interface GenderRepository extends CrudRepository<Gender, Integer> {
}
