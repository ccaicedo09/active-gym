package com.activegym.activegym.Services;


import com.activegym.activegym.DTO.UserDTO;
import com.activegym.activegym.DTO.UserResponseDTO;
import com.activegym.activegym.Entities.User;
import com.activegym.activegym.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public Iterable<UserResponseDTO> findAll() {
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        users.forEach(this::setAge);
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        setAge(user);
        return convertToDTO(user);
    }

    public User create(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        user.setPassword(userDTO.getId().toString()); // Default password, should be changed by User
        return userRepository.save(user);
    }

    private void setAge(User user) {
        if (user.getDateOfBirth() != null) {
            user.setAge(calculateAge(user.getDateOfBirth()));
        }
    }

    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
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
