package com.activegym.activegym.service.Memberships;

import com.activegym.activegym.dto.memberships.ExpiringNotificationDTO;
import com.activegym.activegym.dto.memberships.MembershipDTO;
import com.activegym.activegym.dto.memberships.MembershipFreezeDTO;
import com.activegym.activegym.dto.memberships.MembershipResponseDTO;
import com.activegym.activegym.dto.memberships.MembershipSalesDTO;
import com.activegym.activegym.dto.memberships.MembershipTransferDTO;
import com.activegym.activegym.exceptions.MembershipNotFoundException;
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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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

    /**
     * Retrieves the total number of active memberships.
     * @return the total number of active memberships.
     */
    public Long getActiveMembershipsCount() {
        return membershipRepository.countActiveMemberships();
    }

    /**
     * Transfers a membership from one user to another, given the membership ID and the new user ID.
     * If the membership is transferable and active, has never been transferred and new user has no
     * active membership, the operation is successful and the new owner is set and so is the transferred flag.
     * @param transferDTO the request body containing the membership ID and the new user ID.
     */
    @Transactional
    public void transferMembership(MembershipTransferDTO transferDTO) {
        Membership membership = membershipRepository.findById(transferDTO.getMembershipId())
                .orElseThrow(() -> new MembershipNotFoundException(""));

        if (!membership.getMembershipStatus().getDescription().equals("ACTIVA")) {
            throw new IllegalStateException("No se puede transferir una membresía en estado " + membership.getMembershipStatus().getDescription());
        }

        if (membership.isTransferred()) {
            throw new IllegalStateException("Esta membresía ya ha sido transferida.");
        }

        if (!membership.getMembershipType().isTransferable()) {
            throw new IllegalStateException("Este tipo de membresía no se puede transferir.");
        }

        User newOwner = userRepository.findByDocument(transferDTO.getNewUserDocument())
                .orElseThrow(() -> new UserNotFoundException("Este usuario no existe."));
        boolean hasActiveMembership = membershipRepository.existsActiveMembership(newOwner.getId());
        if (hasActiveMembership) {
            throw new IllegalStateException("El nuevo usuario ya tiene una membresía activa.");
        }

        membership.setUserId(newOwner);
        membership.setTransferred(true);
        membershipRepository.save(membership);
    }

    @Transactional
    public void freezeMembership(MembershipFreezeDTO membershipFreezeDTO) {
        Membership membership = membershipRepository.findById(membershipFreezeDTO.getMembershipId())
                .orElseThrow(() -> new MembershipNotFoundException(""));

        if (!membership.getMembershipStatus().getDescription().equals("ACTIVA")) {
            throw new IllegalStateException("No se puede congelar una membresía en estado " + membership.getMembershipStatus().getDescription());
        }

        if (membership.isFrozen()) {
            throw new IllegalStateException("Esta membresía ya ha sido congelada.");
        }

        if (!membership.getMembershipType().isFreezable()) {
            throw new IllegalStateException("Este tipo de membresía no se puede congelar.");
        }

        int freezeDays = membershipFreezeDTO.getDays();
        if (freezeDays > 15) {
            throw new IllegalStateException("No se puede congelar una membresía por más de 15 días.");
        }
        if (freezeDays < 1) {
            throw new IllegalStateException("Introduce una cantidad de días válida.");
        }

        membership.setFreezeDate(LocalDate.now());
        membership.setUnfreezeDate(LocalDate.now().plusDays(freezeDays));
        membership.setEndDate(membership.getEndDate().plusDays(freezeDays));
        membership.setFrozen(true);
        membership.setMembershipStatus(membershipStatusRepository.findByDescription("CONGELADA")
                .orElseThrow(() -> new MembershipStatusNotFoundException("")));

        membershipRepository.save(membership);

    }

    public List<ExpiringNotificationDTO> findExpiringMemberships() {
        LocalDate today = LocalDate.now();
        LocalDate warningDate = today.plusDays(1);
        List<Membership> expiringMemberships = membershipRepository.findByEndDateBetween(today, warningDate);
        return expiringMemberships.stream()
                .map(m -> new ExpiringNotificationDTO(
                        m.getUserId().getFirstName() + " " + m.getUserId().getLastName(),
                        m.getUserId().getDocument(),
                        m.getUserId().getPhone(),
                        m.getMembershipType().getName(),
                        m.getEndDate()))
                .toList();
    }
}
