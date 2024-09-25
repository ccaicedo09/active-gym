package com.activegym.activegym.Services.Users;

import com.activegym.activegym.Entities.Users.BloodRh;
import com.activegym.activegym.Repositories.Users.BloodRhRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BloodRhService {

    private BloodRhRepository bloodRhRepository;

    public Iterable<BloodRh> findAll() {
        return bloodRhRepository.findAll();
    }

    public BloodRh findById(Integer id) {
        return bloodRhRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("BloodRh not found"));
    }
}
