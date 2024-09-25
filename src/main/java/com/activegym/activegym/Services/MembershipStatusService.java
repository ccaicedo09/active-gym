package com.activegym.activegym.Services;


import com.activegym.activegym.Entities.MembershipStatus;
import com.activegym.activegym.Repositories.MembershipStatusRepository;
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
