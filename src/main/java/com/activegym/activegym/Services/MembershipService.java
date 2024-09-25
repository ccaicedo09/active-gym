package com.activegym.activegym.Services;


import com.activegym.activegym.DTO.MembershipDTO;
import com.activegym.activegym.Entities.Membership;
import com.activegym.activegym.Entities.MembershipStatus;
import com.activegym.activegym.Entities.MembershipType;
import com.activegym.activegym.Entities.User;
import com.activegym.activegym.Repositories.MembershipRepository;
import com.activegym.activegym.Repositories.MembershipStatusRepository;
import com.activegym.activegym.Repositories.MembershipTypeRepository;
import com.activegym.activegym.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class MembershipService {

    private UserRepository userRepository;
    private MembershipTypeRepository membershipTypeRepository;
    private MembershipStatusRepository membershipStatusRepository;
    private final MembershipRepository membershipRepository;
    private final ModelMapper mapper;

    public Membership create(MembershipDTO membershipDTO) {
        // Search user
        User user = userRepository
                .findByDocument(membershipDTO.getUserDocument())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Search membership type
        MembershipType membershipType = membershipTypeRepository
                .findByName(membershipDTO.getMembershipTypeName())
                .orElseThrow(() -> new RuntimeException("Membership type not found"));

        // Calculate end date
        LocalDate startDate = membershipDTO.getStartDate() != null ? membershipDTO.getStartDate() : LocalDate.now();
        int membershipDurationInDays = Integer.parseInt(membershipType.getDuration());
        LocalDate endDate = startDate.plusDays(membershipDurationInDays);

        // Establish membership status (active by default)
        MembershipStatus defaultStatus = membershipStatusRepository
                .findByDescription("ACTIVA")
                .orElseThrow(() -> new RuntimeException("Membership status not found"));

        Membership membership = mapper.map(membershipDTO, Membership.class);
        membership.setUserDocument(user);
        membership.setMembershipType(membershipType);
        membership.setEndDate(endDate);
        membership.setSaleDate(LocalDate.now());
        membership.setMembershipStatus(defaultStatus);

        return membershipRepository.save(membership);
    }
}
