package com.activegym.activegym.service.Users.auxiliary;


import com.activegym.activegym.exceptions.EpsNotFoundException;
import com.activegym.activegym.model.Users.auxiliary.Eps;
import com.activegym.activegym.repository.Users.auxiliary.EpsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for managing EPS-related operations.
 * This class contains the business logic for retrieving EPS information.
 * <p>As mentioned in {@link com.activegym.activegym.service.Users.UserService}, make sure you use this service for retrieving all the EPS records before receiving any user input.</p>
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@AllArgsConstructor
@Service
public class EpsService {

    private EpsRepository epsRepository;

    /**
     * Retrieves all EPS records.
     *
     * @return an iterable collection of all EPS records.
     */
    public Iterable<Eps> findAll() {
        return epsRepository.findAll();
    }

    /**
     * Retrieves an EPS record by its ID.
     *
     * @param id the ID of the EPS record to retrieve.
     * @return the EPS record.
     * @throws EpsNotFoundException if no EPS record is found with the given ID.
     */
    public Eps findById(Integer id) {
        return epsRepository
                .findById(id)
                .orElseThrow(() -> new EpsNotFoundException(""));
    }
}
