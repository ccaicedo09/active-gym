package com.activegym.activegym.service.Users.auxiliary;


import com.activegym.activegym.model.Users.auxiliary.Eps;
import com.activegym.activegym.repository.Users.auxiliary.EpsRepository;
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
