package com.activegym.activegym.Services.Memberships;


import com.activegym.activegym.Entities.Memberships.MembershipStatus;
import com.activegym.activegym.Repositories.Memberships.MembershipStatusRepository;
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
