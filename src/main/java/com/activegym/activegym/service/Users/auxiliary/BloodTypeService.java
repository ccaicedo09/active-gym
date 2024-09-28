package com.activegym.activegym.service.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.BloodType;
import com.activegym.activegym.repository.Users.auxiliary.BloodTypeRepository;
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
