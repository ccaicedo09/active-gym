package com.activegym.activegym.service.Memberships;

import com.activegym.activegym.dto.MembershipDTO;
import com.activegym.activegym.dto.MembershipResponseDTO;
import com.activegym.activegym.model.Memberships.Membership;
import com.activegym.activegym.model.Memberships.MembershipStatus;
import com.activegym.activegym.model.Memberships.MembershipType;
import com.activegym.activegym.model.Users.User;
import com.activegym.activegym.repository.Memberships.MembershipRepository;
import com.activegym.activegym.repository.Memberships.MembershipStatusRepository;
import com.activegym.activegym.repository.Memberships.MembershipTypeRepository;
import com.activegym.activegym.repository.Users.UserRepository;
import com.activegym.activegym.util.ConvertToResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final MembershipTypeRepository membershipTypeRepository;
    private final MembershipStatusRepository membershipStatusRepository;

    public List<MembershipResponseDTO> getAllMemberships() {
        List<Membership> memberships = membershipRepository.findAll();

        return memberships.stream()
                .map(ConvertToResponse::convertToMembershipResponseDTO)
                .toList();
    }

    public MembershipResponseDTO getUserMemberships(String document) {
        User user = userRepository.findByDocument(document)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Membership membership = membershipRepository.findByUserId(user)
                .orElseThrow(() -> new RuntimeException("Membership not found"));

        return ConvertToResponse.convertToMembershipResponseDTO(membership);
    }

    public MembershipResponseDTO create(MembershipDTO membershipDTO){

        Membership membership = mapper.map(membershipDTO, Membership.class);

        User user = userRepository.findByDocument(membershipDTO.getUserDocument())
                .orElseThrow(() -> new RuntimeException("User (member) not found"));

        User soldBy = userRepository.findByDocument(membershipDTO.getSoldByDocument())
                .orElseThrow(() -> new RuntimeException("User (seller) not found"));

        MembershipType membershipType = membershipTypeRepository.findByName(membershipDTO.getMembershipTypeName())
                .orElseThrow(() -> new RuntimeException("Membership type not found"));

        MembershipStatus membershipStatus = membershipStatusRepository.findByDescription("ACTIVA") // Should be changed by scheduled task
                .orElseThrow(() -> new RuntimeException("Estado no encontrado!"));

        membership.setUserId(user);
        membership.setSoldBy(soldBy);
        membership.setMembershipType(membershipType);
        membership.setMembershipStatus(membershipStatus);

        LocalDate startDate = membershipDTO.getStartDate() != null ? membershipDTO.getStartDate() : LocalDate.now();
        int membershipDurationDays = membershipType.getDuration();
        LocalDate endDate = startDate.plusDays(membershipDurationDays);

        membership.setStartDate(startDate);
        membership.setSaleDate(LocalDate.now());
        membership.setEndDate(endDate);

        Membership savedMembership = membershipRepository.save(membership);

        return ConvertToResponse.convertToMembershipResponseDTO(savedMembership);
    }
}
