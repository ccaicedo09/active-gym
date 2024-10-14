package com.activegym.activegym.controller.Memberships;

import com.activegym.activegym.dto.MembershipDTO;
import com.activegym.activegym.dto.MembershipResponseDTO;
import com.activegym.activegym.dto.MembershipTypeDTO;
import com.activegym.activegym.model.Memberships.MembershipType;
import com.activegym.activegym.service.Memberships.MembershipService;
import com.activegym.activegym.service.Memberships.MembershipTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@AllArgsConstructor
@RequestMapping("api/memberships")
@RestController
public class MembershipController {

    private final MembershipService membershipService;
    private final MembershipTypeService membershipTypeService;

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @GetMapping
    public ResponseEntity<List<MembershipResponseDTO>> getAllMemberships() {
        List<MembershipResponseDTO> memberships = membershipService.getAllMemberships();
        return ResponseEntity.ok(memberships);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @GetMapping("/{document}")
    public ResponseEntity<List<MembershipResponseDTO>> getUserMemberships(@PathVariable("document") String document) {

        List<MembershipResponseDTO> memberships = membershipService.getUserMemberships(document);

        return ResponseEntity.ok(memberships);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @PostMapping
    public MembershipResponseDTO create(@RequestBody MembershipDTO membershipDTO) {
        return membershipService.create(membershipDTO);
    }

    // Membership types endpoints

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/types/create")
    public MembershipType create(@RequestBody MembershipTypeDTO membershipTypeDTO) {
        return membershipTypeService.create(membershipTypeDTO);
    }

    @GetMapping("/public/types")
    public Iterable<MembershipType> list() {
        return membershipTypeService.findAll();
    }

}
