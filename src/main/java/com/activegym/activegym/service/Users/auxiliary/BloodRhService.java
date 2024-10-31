package com.activegym.activegym.service.Users.auxiliary;

import com.activegym.activegym.exceptions.BloodRhNotFoundException;
import com.activegym.activegym.model.Users.auxiliary.BloodRh;
import com.activegym.activegym.repository.Users.auxiliary.BloodRhRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Blood RH-related operations.
 * This class contains the business logic for retrieving Blood RH information.
 *<p>As mentioned in {@link com.activegym.activegym.service.Users.UserService}, make sure you use this service for retrieving all the Blood RH factors before receiving any user input.</p>
 * @author Carlos Esteban Castro Caicedo
 * @since v1.0
 */
@AllArgsConstructor
@Service
public class BloodRhService {

    private BloodRhRepository bloodRhRepository;

    /**
     * Finds all Blood Rh records.
     *
     * @return and iterable collection of all Blood Rh records.
     */
    public Iterable<BloodRh> findAll() {
        return bloodRhRepository.findAll();
    }

    /**
     * Retrieves a Blood Rh record by its ID.
     *
     * @param id the ID of the Blood Rh record to retrieve.
     * @return the Blood Rh record.
     * @throws BloodRhNotFoundException if the Blood Rh record is not found.
     */
    public BloodRh findById(Integer id) {
        return bloodRhRepository
                .findById(id)
                .orElseThrow(() -> new BloodRhNotFoundException("BloodRh not found"));
    }
}
