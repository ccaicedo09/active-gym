package com.activegym.activegym.Services.Users;

import com.activegym.activegym.DTO.UserDTO;
import com.activegym.activegym.DTO.UserResponseDTO;
import com.activegym.activegym.Entities.Users.User;
import com.activegym.activegym.Repositories.Users.UserRepository;
import com.activegym.activegym.Utils.AgeCalculator;
import com.activegym.activegym.Utils.AuxiliarFields;
import com.activegym.activegym.Utils.UserResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final AuxiliarFields auxiliarFields;
    private final AgeCalculator ageCalculator;
    private final UserResponse userResponse;

    public Iterable<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .peek(ageCalculator::setAge)
                .map(userResponse::convertToResponseDTO)
                .collect(Collectors.toSet());
    }

    public UserResponseDTO findByDocument(String document) {
        User user = userRepository
                .findByDocument(document)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        ageCalculator.setAge(user);
        return userResponse.convertToResponseDTO(user);
    }

    public User create(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        auxiliarFields.castUserAuxiliarFields(userDTO, user);
        user.setPassword(userDTO.getDocument()); // Default password, should be changed by User
        return userRepository.save(user);
    }

    public User update(String document, UserDTO userDTO) {
        User user = userRepository
                .findByDocument(document)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        auxiliarFields.castUserAuxiliarFields(userDTO, user);
        return userRepository.save(user);
    }
}
