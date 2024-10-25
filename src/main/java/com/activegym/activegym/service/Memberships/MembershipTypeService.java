package com.activegym.activegym.service.Memberships;


import com.activegym.activegym.dto.MembershipTypeDTO;
import com.activegym.activegym.model.Memberships.MembershipType;
import com.activegym.activegym.repository.Memberships.MembershipTypeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MembershipTypeService {

    private final MembershipTypeRepository membershipTypeRepository;
    private final ModelMapper mapper;

    public Iterable<MembershipType> findAll() {
        return membershipTypeRepository.findAll();
    }

    public MembershipType findById(Long id) {
        return membershipTypeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("MembershipType not found"));
    }

    public MembershipType create(MembershipTypeDTO membershipTypeDTO) {
        MembershipType membershipType = mapper.map(membershipTypeDTO, MembershipType.class);
        return membershipTypeRepository.save(membershipType);
    }

    public MembershipType edit(Long id, MembershipTypeDTO membershipTypeDTO) {
        MembershipType membershipType = findById(id);
        mapper.map(membershipTypeDTO, membershipType);
        return membershipTypeRepository.save(membershipType);
    }

    public void toggleVisibility(Long id) {
        MembershipType membershipType = findById(id);
        membershipType.setVisible(!membershipType.isVisible());
        membershipTypeRepository.save(membershipType);
    }
}
