package com.activegym.activegym.service.Users.auxiliary;

import com.activegym.activegym.exceptions.BloodTypeNotFoundException;
import com.activegym.activegym.model.Users.auxiliary.BloodType;
import com.activegym.activegym.repository.Users.auxiliary.BloodTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Blood Type-related operations.
 * This class contains the business logic for retrieving Blood Type information.
 *<p>As mentioned in {@link com.activegym.activegym.service.Users.UserService}, make sure you use this service for retrieving all the Blood Types before receiving any user input.</p>
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@AllArgsConstructor
@Service
public class BloodTypeService {

    private BloodTypeRepository bloodTypeRepository;

    /**
     * Retrieves all Blood Type records.
     *
     * @return an iterable collection of all Blood Type records.
     */
    public Iterable<BloodType> findAll() {
        return bloodTypeRepository.findAll();
    }

    /**
     * Retrieves a Blood Type record by its ID.
     *
     * @param id the ID of the Blood Type record to retrieve.
     * @return the Blood Type record.
     * @throws BloodTypeNotFoundException if no Blood Type record is found with the given ID.
     */
    public BloodType findById(Integer id) {
        return bloodTypeRepository
                .findById(id)
                .orElseThrow(() -> new BloodTypeNotFoundException(""));
    }
}
