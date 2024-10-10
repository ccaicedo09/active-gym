package com.activegym.activegym.util;

import com.activegym.activegym.model.Memberships.Membership;
import com.activegym.activegym.model.Memberships.MembershipStatus;
import com.activegym.activegym.repository.Memberships.MembershipRepository;
import com.activegym.activegym.repository.Memberships.MembershipStatusRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledTasks {

    private final MembershipRepository membershipRepository;
    private final MembershipStatusRepository membershipStatusRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateMembershipStatuses() {

        MembershipStatus inactiveStatus = membershipStatusRepository.findByDescription("INACTIVA")
                .orElseThrow(() -> new RuntimeException("Membership status not found"));

        MembershipStatus activeStatus = membershipStatusRepository.findByDescription("ACTIVA")
                .orElseThrow(() -> new RuntimeException("Membership status not found"));

        List<Membership> activeMemberships = membershipRepository.findByMembershipStatus(activeStatus);

        LocalDate currentDate = LocalDate.now();

        for (Membership membership : activeMemberships) {
            if (membership.getEndDate().isBefore(currentDate)) {

                membership.setMembershipStatus(inactiveStatus);
                membershipRepository.save(membership);

            }
        }

        System.out.println("Membership statuses updated");

    }

    @PostConstruct
    public void onStartUp() {
        updateMembershipStatuses();
    }

}
