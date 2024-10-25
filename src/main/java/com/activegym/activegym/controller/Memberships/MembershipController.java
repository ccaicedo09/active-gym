package com.activegym.activegym.controller.Memberships;

import com.activegym.activegym.dto.MembershipDTO;
import com.activegym.activegym.dto.MembershipResponseDTO;
import com.activegym.activegym.dto.MembershipTypeDTO;
import com.activegym.activegym.dto.ResponseStatusMessage;
import com.activegym.activegym.model.Memberships.MembershipType;
import com.activegym.activegym.service.Memberships.MembershipService;
import com.activegym.activegym.service.Memberships.MembershipTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://activegym.vercel.app/"})
@AllArgsConstructor
@RequestMapping("api/memberships")
@RestController
@Tag(name = "Memberships", description = "Memberships endpoints")
public class MembershipController {

    private final MembershipService membershipService;
    private final MembershipTypeService membershipTypeService;
    ResponseStatusMessage responseStatusMessage;

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @GetMapping
    @Operation(summary = "MANAGEMENT: Get all memberships", description = "Get all memberships including all of the statuses and using pagination")
    public ResponseEntity<Page<MembershipResponseDTO>> getAllMemberships(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<MembershipResponseDTO> memberships = membershipService.getAllMemberships(page, size);
        return ResponseEntity.ok(memberships);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @GetMapping("/{document}")
    @Operation(summary = "MANAGEMENT: Get user memberships", description = "Get all memberships of a user")
    public ResponseEntity<List<MembershipResponseDTO>> getUserMemberships(@PathVariable("document") String document) {

        List<MembershipResponseDTO> memberships = membershipService.getUserMemberships(document);

        return ResponseEntity.ok(memberships);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @PostMapping
    @Operation(summary = "MANAGEMENT: Create a new membership", description = "Create a new membership for a user")
    public ResponseEntity<ResponseStatusMessage> create(@RequestBody MembershipDTO membershipDTO) {
        membershipService.create(membershipDTO);
        responseStatusMessage.setMessage("Membership created");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseStatusMessage);
    }

    // Membership types endpoints

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/types/create")
    @Operation(summary = "ADMIN: Create membership type", description = "Create a new membership type")
    public MembershipType create(@RequestBody MembershipTypeDTO membershipTypeDTO) {
        return membershipTypeService.create(membershipTypeDTO);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PatchMapping("/types/edit/{id}")
    @Operation(summary = "ADMIN: Edit membership type", description = "Edit a membership type")
    public MembershipType edit(@PathVariable("id") Long id, @RequestBody MembershipTypeDTO membershipTypeDTO) {
        return membershipTypeService.edit(id, membershipTypeDTO);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PatchMapping("/types/toggle-visibility/{id}")
    @Operation(summary = "ADMIN: Toggle visibility", description = "Toggle visibility of a membership type")
    public void toggleVisibility(@PathVariable("id") Long id) {
        membershipTypeService.toggleVisibility(id);
    }

    @GetMapping("/public/types")
    @Operation(summary = "PUBLIC: List membership types", description = "List all membership types")
    public Iterable<MembershipType> list() {
        return membershipTypeService.findAll();
    }

}
