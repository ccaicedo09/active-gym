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
import com.activegym.activegym.util.ExtractCurrentSessionDocument;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
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
    private final ExtractCurrentSessionDocument extractCurrentSessionDocument;

    // Returns all memberships, ordered by end date
    public List<MembershipResponseDTO> getAllMemberships() {
        List<Membership> memberships = membershipRepository.findAll(Sort.by(Sort.Direction.DESC, "endDate"));

        return memberships.stream()
                .map(ConvertToResponse::convertToMembershipResponseDTO)
                .toList();
    }

    // Returns all memberships of a user, ordered by end date (active membership first)
    public List<MembershipResponseDTO> getUserMemberships(String document) {
        User user = userRepository.findByDocument(document)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Membership> memberships = membershipRepository.findAllByUserIdOrderByEndDateDesc(user);

        return memberships.stream()
                .map(ConvertToResponse::convertToMembershipResponseDTO)
                .toList();
    }

    // Create a new membership
    public MembershipResponseDTO create(MembershipDTO membershipDTO){

        Membership membership = mapper.map(membershipDTO, Membership.class);

        User user = userRepository.findByDocument(membershipDTO.getUserDocument())
                .orElseThrow(() -> new RuntimeException("User (member) not found"));

        String sellerDocument = extractCurrentSessionDocument.extractDocument();

        User soldBy = userRepository.findByDocument(sellerDocument)
                .orElseThrow(() -> new RuntimeException("User (seller) not found"));

        MembershipType membershipType = membershipTypeRepository.findByName(membershipDTO.getMembershipType())
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
