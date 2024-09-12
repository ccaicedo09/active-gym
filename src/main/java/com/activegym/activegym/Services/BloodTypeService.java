package com.activegym.activegym.Services;

import com.activegym.activegym.Entities.BloodType;
import com.activegym.activegym.Repositories.BloodTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BloodTypeService {

    private BloodTypeRepository bloodTypeRepository;

    public Iterable<BloodType> findAll() {
        return bloodTypeRepository.findAll();
    }

    public BloodType findById(Integer id) {
        return bloodTypeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("BloodType not found"));
    }
}
