package com.activegym.activegym.Services.Users;


import com.activegym.activegym.Entities.Users.Eps;
import com.activegym.activegym.Repositories.Users.EpsRepository;
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
