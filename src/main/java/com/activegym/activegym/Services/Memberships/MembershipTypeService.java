package com.activegym.activegym.Services.Memberships;


import com.activegym.activegym.DTO.MembershipTypeDTO;
import com.activegym.activegym.Entities.Memberships.MembershipType;
import com.activegym.activegym.Repositories.Memberships.MembershipTypeRepository;
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
}
