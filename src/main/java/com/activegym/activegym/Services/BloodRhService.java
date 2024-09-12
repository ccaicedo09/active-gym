package com.activegym.activegym.Services;

import com.activegym.activegym.Entities.BloodRh;
import com.activegym.activegym.Repositories.BloodRhRepository;
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
