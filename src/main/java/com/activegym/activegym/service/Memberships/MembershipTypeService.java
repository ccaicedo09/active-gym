package com.activegym.activegym.service.Memberships;


import com.activegym.activegym.dto.MembershipTypeDTO;
import com.activegym.activegym.exceptions.MembershipTypeNotFoundException;
import com.activegym.activegym.model.Memberships.MembershipType;
import com.activegym.activegym.repository.Memberships.MembershipTypeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * Service for managing all operation related to membership types (plans) for the establishment.
 * Contains the business logic for creating, updating, deleting and getting membership types.
 * Also, it contains the logic for toggling the visibility of a membership type.
 * @author Carlos Esteban Castro Caicedo
 * @since v1.0
 */
@AllArgsConstructor
@Service
public class MembershipTypeService {

    /**
     * Inject membership type repository and model mapper for mapping DTOs to entities and vice versa.
     */
    private final MembershipTypeRepository membershipTypeRepository;
    private final ModelMapper mapper;

    /**
     * Find all membership types.
     *
     * @return an iterable of all membership types.
     */
    public Iterable<MembershipType> findAll() {
        return membershipTypeRepository.findAll();
    }

    /**
     * Locally used method: find membership type by id for setting relationships.
     *
     * @param id the membership type id.
     * @return the membership type
     */
    public MembershipType findById(Long id) {
        return membershipTypeRepository
                .findById(id)
                .orElseThrow(() -> new MembershipTypeNotFoundException(""));
    }

    /**
     * Create membership type.
     *
     * @param membershipTypeDTO as the request expected body for creating a membership type, this DTO is mapped into the membershipType entity with model mapper.
     * @return save the created membership type.
     */
    public MembershipType create(MembershipTypeDTO membershipTypeDTO) {
        MembershipType membershipType = mapper.map(membershipTypeDTO, MembershipType.class);
        return membershipTypeRepository.save(membershipType);
    }

    /**
     * Edit membership type.
     *
     * @param id The id of the membership type to be edited, used for finding the membership type through the {@link #findById(Long)} method.
     * @param membershipTypeDTO as the request expected body for editing a membership type, this DTO is mapped into the membershipType entity with model mapper.
     * @return save the edited membership type.
     */
    public MembershipType edit(Long id, MembershipTypeDTO membershipTypeDTO) {
        MembershipType membershipType = findById(id);
        mapper.map(membershipTypeDTO, membershipType);
        return membershipTypeRepository.save(membershipType);
    }

    /**
     * Toggle visibility of a membership type. If the membership type is visible, it will be hidden and vice versa.
     *
     * @param id the id of the membership type to toggle visibility. Used for finding the membership type through the {@link #findById(Long)} method.
     */
    public void toggleVisibility(Long id) {
        MembershipType membershipType = findById(id);
        membershipType.setVisible(!membershipType.isVisible());
        membershipTypeRepository.save(membershipType);
    }
}
