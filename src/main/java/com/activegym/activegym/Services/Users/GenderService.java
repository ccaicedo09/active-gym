package com.activegym.activegym.Services.Users;

import com.activegym.activegym.Entities.Users.Gender;
import com.activegym.activegym.Repositories.Users.GenderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GenderService {

    private GenderRepository genderRepository;

    public Iterable<Gender> findAll() {
        return genderRepository.findAll();
    }

    public Gender findById(Integer id) {
        return genderRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Gender not found"));
    }
}
