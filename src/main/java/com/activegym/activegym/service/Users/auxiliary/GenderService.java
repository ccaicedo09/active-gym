package com.activegym.activegym.service.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.Gender;
import com.activegym.activegym.repository.Users.auxiliary.GenderRepository;
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
