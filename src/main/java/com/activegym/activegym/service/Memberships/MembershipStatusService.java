package com.activegym.activegym.service.Memberships;


import com.activegym.activegym.model.Memberships.MembershipStatus;
import com.activegym.activegym.repository.Memberships.MembershipStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MembershipStatusService {

    private final MembershipStatusRepository membershipStatusRepository;

    public MembershipStatus findById(Long id) {
        return membershipStatusRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("MembershipStatus not found"));
    }
}
