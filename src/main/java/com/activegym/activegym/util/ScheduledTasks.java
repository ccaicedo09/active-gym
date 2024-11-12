package com.activegym.activegym.util;

import com.activegym.activegym.exceptions.MembershipStatusNotFoundException;
import com.activegym.activegym.model.Memberships.Membership;
import com.activegym.activegym.model.Memberships.MembershipStatus;
import com.activegym.activegym.repository.Memberships.MembershipRepository;
import com.activegym.activegym.repository.Memberships.MembershipStatusRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

/**
 * Service responsible for scheduled tasks related to memberships.
 * <p>
 * This class contains a method that updates membership statuses every day at midnight.
 * It also performs an initial status update when the service starts.
 * </p>
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Service
@RequiredArgsConstructor
public class ScheduledTasks {

    private final MembershipRepository membershipRepository;
    private final MembershipStatusRepository membershipStatusRepository;

    /**
     * Updates the statuses of memberships daily at 00:00 (midnight) or at application runtime.
     * <p>
     * This method fetches all active and frozen memberships and checks if their end dates or unfreeze dates, respectively, have passed.
     * If a membership's end date is before the current date, its status is updated to "INACTIVE" and so happens if the unfreeze date has passed,
     * updating the status to "ACTIVE" back again, then, the change is saved in the database.
     * </p>
     *
     * @throws MembershipStatusNotFoundException if the membership statuses "ACTIVE" or "INACTIVE" are not found.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void updateMembershipStatuses() {

        MembershipStatus inactiveStatus = membershipStatusRepository.findByDescription("INACTIVA")
                .orElseThrow(() -> new MembershipStatusNotFoundException("Membership status not found"));

        MembershipStatus activeStatus = membershipStatusRepository.findByDescription("ACTIVA")
                .orElseThrow(() -> new MembershipStatusNotFoundException("Membership status not found"));

        MembershipStatus frozenStatus = membershipStatusRepository.findByDescription("CONGELADA")
                .orElseThrow(() -> new MembershipStatusNotFoundException("Membership status not found"));

        List<Membership> memberships = membershipRepository.findByMembershipStatusIn(Arrays.asList(activeStatus, frozenStatus));

        LocalDate currentDate = LocalDate.now();

        for (Membership membership : memberships) {
            if (membership.getFreezeDate() != null && membership.getUnfreezeDate() != null) {
                if (!currentDate.isBefore(membership.getUnfreezeDate())) {
                    membership.setFreezeDate(null);
                    membership.setUnfreezeDate(null);
                    membership.setMembershipStatus(activeStatus);
                    membershipRepository.save(membership);
                }
            } else if (membership.getEndDate().isBefore(currentDate)) {
                membership.setMembershipStatus(inactiveStatus);
                membershipRepository.save(membership);
            }
        }
        System.out.println("Membership statuses updated");
    }

    /**
     * Executes an initial membership status update when the application starts.
     * <p>
     * This method ensures that any memberships that should be inactive are updated
     * immediately upon service startup by calling {@link #updateMembershipStatuses()}.
     * </p>
     */
    @PostConstruct
    public void onStartUp() {
        updateMembershipStatuses();
    }
}
