package com.activegym.activegym.Services;


import com.activegym.activegym.Entities.Eps;
import com.activegym.activegym.Repositories.EpsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EpsService {

    private EpsRepository epsRepository;

    public Iterable<Eps> findAll() {
        return epsRepository.findAll();
    }

    public Eps findById(Integer id) {
        return epsRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Eps not found"));
    }
}
