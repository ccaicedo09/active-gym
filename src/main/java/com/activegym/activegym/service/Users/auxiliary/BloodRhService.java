package com.activegym.activegym.service.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.BloodRh;
import com.activegym.activegym.repository.Users.auxiliary.BloodRhRepository;
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
