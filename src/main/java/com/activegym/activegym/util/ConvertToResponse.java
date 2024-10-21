package com.activegym.activegym.util;

import com.activegym.activegym.dto.MembershipResponseDTO;
import com.activegym.activegym.dto.RoleDTO;
import com.activegym.activegym.dto.UserResponseDTO;
import com.activegym.activegym.model.Memberships.Membership;
import com.activegym.activegym.model.Roles.Role;
import com.activegym.activegym.model.Users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConvertToResponse {

    public UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setDocument(user.getDocument());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setAge(user.getAge());
        dto.setGenderName(user.getGender().getGenderName());
        dto.setEpsName(user.getEps().getEpsName());
        dto.setBloodTypeName(user.getBloodType().getBloodTypeName());
        dto.setBloodRhName(user.getBloodRh().getBloodRh());
        Set<String> roleNames = user.getRoles().stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toSet());
        dto.setRoles(roleNames);
        return dto;
    }

    public static MembershipResponseDTO convertToMembershipResponseDTO(Membership membership) {
        MembershipResponseDTO dto = new MembershipResponseDTO();
        dto.setId(membership.getId());
        dto.setUserDocument(membership.getUserId().getDocument());
        dto.setMembershipTypeName(membership.getMembershipType().getName());
        dto.setStartDate(membership.getStartDate());
        dto.setEndDate(membership.getEndDate());
        dto.setSaleDate(membership.getSaleDate());
        dto.setMembershipStatus(membership.getMembershipStatus().getDescription());
        dto.setSoldByDocument(membership.getSoldBy().getDocument());

        return dto;
    }
}
