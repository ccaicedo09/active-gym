package com.activegym.activegym.service.Memberships;


import com.activegym.activegym.exceptions.MembershipStatusNotFoundException;
import com.activegym.activegym.model.Memberships.MembershipStatus;
import com.activegym.activegym.repository.Memberships.MembershipStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for managing operations related to membership status;
 *
 * @author Carlos Esteban Castro Caicedo
 * @deprecated This class is no longer used in the project.
 * @since v1.0
 */
@AllArgsConstructor
@Service
public class MembershipStatusService {

    private final MembershipStatusRepository membershipStatusRepository;

    /**
     * Find by id membership status.
     *
     * @param id the id
     * @return the membership status
     */
    public MembershipStatus findById(Long id) {
        return membershipStatusRepository
                .findById(id)
                .orElseThrow(() -> new MembershipStatusNotFoundException(""));
    }
}
