package com.activegym.activegym.service.Users.auxiliary;

import com.activegym.activegym.exceptions.GenderNotFoundException;
import com.activegym.activegym.model.Users.auxiliary.Gender;
import com.activegym.activegym.repository.Users.auxiliary.GenderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Gender-related operations.
 * This class contains the business logic for retrieving Gender information.
 * <p>As mentioned in {@link com.activegym.activegym.service.Users.UserService}, make sure you use this service for retrieving all the Genders before receiving any user input.</p>
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@AllArgsConstructor
@Service
public class GenderService {

    private GenderRepository genderRepository;

    /**
     * Retrieves all Gender records.
     *
     * @return an iterable collection of all Gender records.
     */
    public Iterable<Gender> findAll() {
        return genderRepository.findAll();
    }

    /**
     * Retrieves a Gender record by its ID.
     *
     * @param id the ID of the Gender record to retrieve.
     * @return the Gender record.
     * @throws GenderNotFoundException if no Gender record is found with the given ID.
     */
    public Gender findById(Integer id) {
        return genderRepository
                .findById(id)
                .orElseThrow(() -> new GenderNotFoundException(""));
    }
}
