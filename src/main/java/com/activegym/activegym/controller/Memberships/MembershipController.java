package com.activegym.activegym.controller.Memberships;

import com.activegym.activegym.dto.MembershipDTO;
import com.activegym.activegym.dto.MembershipResponseDTO;
import com.activegym.activegym.service.Memberships.MembershipService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RequestMapping("/memberships")
@RestController
public class MembershipController {

    private final MembershipService membershipService;

    @GetMapping
    public ResponseEntity<List<MembershipResponseDTO>> getAllMemberships() {
        List<MembershipResponseDTO> memberships = membershipService.getAllMemberships();
        return ResponseEntity.ok(memberships);
    }

    @GetMapping("/{document}")
    public ResponseEntity<MembershipResponseDTO> getUserMemberships(@PathVariable String document) {

        MembershipResponseDTO memberships = membershipService.getUserMemberships(document);

        return ResponseEntity.ok(memberships);
    }

    @PostMapping
    public MembershipResponseDTO create(@RequestBody MembershipDTO membershipDTO) {
        return membershipService.create(membershipDTO);
    }

}
