package com.activegym.activegym.repository.Memberships;

import com.activegym.activegym.dto.memberships.MembershipSalesDTO;
import com.activegym.activegym.model.Memberships.Membership;
import com.activegym.activegym.model.Memberships.MembershipStatus;
import com.activegym.activegym.model.Users.User;
import jakarta.persistence.TemporalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Membership} entities.
 * Provides standard CRUD operations and custom queries related to memberships.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    /**
     * Retrieves a paginated list of all memberships, filtered by the given criteria.
     *
     * @param pageable the pagination information.
     * @return a {@link Page} containing the memberships.
     */
    @Query("SELECT m FROM Membership m " +
        "WHERE (:userDocument IS NULL OR m.userId.document = :userDocument) " +
        "AND (:membershipType IS NULL OR m.membershipType.name = :membershipType) " +
        "AND (:membershipStatus IS NULL OR m.membershipStatus.description = :membershipStatus) " +
        "AND (:frozen IS NULL OR m.frozen = :frozen) " +
        "AND (:transferred IS NULL OR m.transferred = :transferred) " +
        "ORDER BY m.endDate DESC")
    Page<Membership> findAll(
            @Param("userDocument") String userDocument,
            @Param("membershipType") String membershipType,
            @Param("membershipStatus") String membershipStatus,
            @Param("frozen") Boolean frozen,
            @Param("transferred") Boolean transferred,
            Pageable pageable);

    /**
     * Retrieves all memberships for a specific user, ordered by the end date in descending order.
     *
     * @param user the {@link User} whose memberships are to be retrieved.
     * @return a list of memberships associated with the given user, sorted by end date.
     */
    List<Membership> findAllByUserIdOrderByEndDateDesc(User user);

    /**
     * Retrieves all memberships with a specific status.
     *
     * @param membershipStatus the {@link MembershipStatus} to filter memberships by.
     * @return a list of memberships that match the given status.
     */
    List<Membership> findByMembershipStatus(MembershipStatus membershipStatus);

    /**
     * Counts the number of memberships of each type sold in a specific month and year.
     *
     * @param month the month to be consulted.
     * @param year the year to be consulted.
     * @return a list of {@link MembershipSalesDTO} containing the membership type and the number of memberships sold.
     */
    @Query("SELECT new com.activegym.activegym.dto.memberships.MembershipSalesDTO(mt.name, COUNT(m.id)) " +
            "FROM Membership m JOIN m.membershipType mt " +
            "WHERE EXTRACT(MONTH FROM m.saleDate) = :month " +
            "AND EXTRACT(YEAR FROM m.saleDate) = :year " +
            "GROUP BY mt.name ORDER BY COUNT(m.id) DESC")
    List<MembershipSalesDTO> countMembershipsByTypeAndMonth(@Param("month") int month, @Param("year") int year);

    /**
     * Counts the number of memberships sold in a specific month and year.
     *
     * @param month the month to be consulted.
     * @param year the year to be consulted.
     * @return the total number of memberships sold in the given month and year.
     */
        @Query("SELECT COUNT(m) FROM Membership m " +
                "WHERE EXTRACT(MONTH FROM m.saleDate) = :month " +
                "AND EXTRACT(YEAR FROM m.saleDate) = :year")
    Long countMembershipsByMonthAndYear(@Param("month") int month, @Param("year") int year);

    /**
     * Calculates the total earnings in a specific month and year.
     * @param month the month to be consulted.
     * @param year the year to be consulted.
     * @return the total earnings in the given month and year.
     */
    @Query("SELECT SUM(m.paidAmount) FROM Membership m " +
            "WHERE EXTRACT(MONTH FROM m.saleDate) = :month " +
            "AND EXTRACT(YEAR FROM m.saleDate) = :year")
    Double calculateTotalEarningsByMonthAndYear(@Param("month") int month, @Param("year") int year);

    /**
     * Counts the number of active memberships.
     * @return the total number of active memberships.
     */
    @Query("SELECT COUNT(m) FROM Membership m WHERE m.endDate > CURRENT_DATE")
    Long countActiveMemberships();

    /**
     * Checks if a user has an active membership.
     * @param userId the id of the user to be checked.
     * @return true if the user has an active membership, false otherwise.
     */
    @Query("SELECT COUNT(m) > 0 FROM Membership m WHERE m.userId.id = :userId AND m.endDate >= CURRENT_DATE AND m.membershipStatus.description = 'ACTIVA'")
    boolean existsActiveMembership(@Param("userId") Long userId);

    /**
     * Retrieves all memberships that expire between two dates.
     * @param start the start date of the range.
     * @param end the end date of the range.
     * @return a list of memberships that expire between the given dates.
     */
    List<Membership> findByEndDateBetween(LocalDate start, LocalDate end);

    /**
     * Retrieves all memberships with a specific status.
     * @param statuses the list of {@link MembershipStatus} to filter memberships by.
     * @return a list of memberships that match the given statuses.
     * @since v1.1
     */
    List<Membership> findByMembershipStatusIn(List<MembershipStatus> statuses);

    /**
     * Retrieves the first membership (the ACTIVA one if exists) of a user with a specific status, ordered by end date in descending order.
     * @param user the {@link User} whose memberships are to be retrieved.
     * @param status the status to filter memberships by.
     * @return an {@link Optional} containing the first membership that matches the given status.
     * @since v1.3
     */
    Optional<Membership> findFirstByUserIdAndMembershipStatusDescriptionOrderByEndDateDesc(User user, String status);
}
