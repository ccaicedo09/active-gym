package com.activegym.activegym.Utils;

import com.activegym.activegym.DTO.UserResponseDTO;
import com.activegym.activegym.Entities.Users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserResponse {

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
        return dto;
    }
}
