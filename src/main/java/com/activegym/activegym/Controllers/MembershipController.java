package com.activegym.activegym.Controllers;

import com.activegym.activegym.DTO.MembershipDTO;
import com.activegym.activegym.Entities.Membership;
import com.activegym.activegym.Services.MembershipService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@AllArgsConstructor
@RequestMapping("/memberships")
@RestController
public class MembershipController {

    private final MembershipService membershipService;

    @PostMapping
    public Membership create(@RequestBody MembershipDTO membershipDTO) {
        return membershipService.create(membershipDTO);
    }
}
