package com.activegym.activegym.service.Memberships;

import com.activegym.activegym.dto.memberships.MembershipDTO;
import com.activegym.activegym.dto.memberships.MembershipResponseDTO;
import com.activegym.activegym.dto.memberships.MembershipSalesDTO;
import com.activegym.activegym.exceptions.MembershipStatusNotFoundException;
import com.activegym.activegym.exceptions.MembershipTypeNotFoundException;
import com.activegym.activegym.exceptions.UserNotFoundException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service for managing operations related to users' memberships;
 * containing the business logic for creating, updating, deleting and getting memberships.
 *
 * @author Carlos Esteban Castro Caicedo
 * @since v1.0
 */
@AllArgsConstructor
@Service
public class MembershipService {

    /**
     * Inject necessary repositories and external services:
     * <ul>
     *      <li> Repositories for CRUD operations of the respective entities.</li>
     *      <li> ModelMapper for mapping DTOs to entities and vice versa.</li>
     *      <li> ExtractCurrentSessionDocument for getting the document of the current session user using
     *           the JWT token.</li>
     *  </ul>
     */
    private final MembershipRepository membershipRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final MembershipTypeRepository membershipTypeRepository;
    private final MembershipStatusRepository membershipStatusRepository;
    private final ExtractCurrentSessionDocument extractCurrentSessionDocument;

    /**
     * List all memberships with pagination.
     *
     * @param page the page number
     * @param size the number of memberships per page.
     * @return all memberships ordered by end date, ensuring active memberships are shown first.
     */
    public Page<MembershipResponseDTO> getAllMemberships(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "endDate"));

        return membershipRepository.findAll(pageable)
                .map(ConvertToResponse::convertToMembershipResponseDTO);
    }

    /**
     * List a specific user's memberships, ordered by end date in descending order (most recent first).
     *
     * @param document The document of the user whose memberships are to be consulted.
     * @return A list of objects {@code MembershipResponseDTO} containing the user's memberships information.
     *
     * @throws UserNotFoundException If no user is found with the given document.
     */
    public List<MembershipResponseDTO> getUserMemberships(String document) {
        User user = userRepository.findByDocument(document)
                .orElseThrow(() -> new UserNotFoundException(""));

        List<Membership> memberships = membershipRepository.findAllByUserIdOrderByEndDateDesc(user);

        return memberships.stream()
                .map(ConvertToResponse::convertToMembershipResponseDTO)
                .toList();
    }

    /**
     * Create new membership for a user.
     *
     * @param membershipDTO expected request body containing the membership information.
     * @return {@code MembershipResponseDTO} object containing the membership information.
     * @throws UserNotFoundException If no user is found with the given document.
     * @throws MembershipTypeNotFoundException If no membership type is found with the given name.
     * @throws MembershipStatusNotFoundException If no membership status is found with the given description.
     */
    public MembershipResponseDTO create(MembershipDTO membershipDTO){

        Membership membership = mapper.map(membershipDTO, Membership.class);

        User user = userRepository.findByDocument(membershipDTO.getUserDocument())
                .orElseThrow(() -> new UserNotFoundException(""));

        String sellerDocument = extractCurrentSessionDocument.extractDocument();

        User soldBy = userRepository.findByDocument(sellerDocument)
                .orElseThrow(() -> new UserNotFoundException(""));

        MembershipType membershipType = membershipTypeRepository.findByName(membershipDTO.getMembershipType())
                .orElseThrow(() -> new MembershipTypeNotFoundException(""));

        MembershipStatus membershipStatus = membershipStatusRepository.findByDescription("ACTIVA") // Should be changed by scheduled task
                .orElseThrow(() -> new MembershipStatusNotFoundException(""));

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

    /**
     * Retrieves a list containing the count of sales per membership type in a specific month and year.
     * @param month the month to be consulted.
     * @param year the year to be consulted.
     * @return a list of {@link MembershipSalesDTO} containing the membership type and the number of memberships sold.
     */
    public List<MembershipSalesDTO> getTopSoldMemberships(int month, int year) {
        return membershipRepository.countMembershipsByTypeAndMonth(month, year);
    }

    /**
     * Retrieves the total number of memberships sold in a specific month and year.
     * @param month the month to be consulted.
     * @param year the year to be consulted.
     * @return the total number of memberships sold in the given month and year.
     */
    public Long getTotalMembershipsSold(int month, int year) {
        return membershipRepository.countMembershipsByMonthAndYear(month, year);
    }

    /**
     * Retrieves the total earnings from memberships sold in a specific month and year.
     * @param month the month to be consulted.
     * @param year the year to be consulted.
     * @return the total earnings from memberships sold in the given month and year.
     */
    public Double getTotalEarnings(int month, int year) {
        Double totalEarnings = membershipRepository.calculateTotalEarningsByMonthAndYear(month, year);
        return totalEarnings != null ? totalEarnings : 0.0;
    }

    public Long getActiveMembershipsCount() {
        return membershipRepository.countActiveMemberships();
    }
}
