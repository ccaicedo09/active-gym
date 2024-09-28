package com.activegym.activegym.service.Users;

import com.activegym.activegym.dto.UserDTO;
import com.activegym.activegym.dto.UserResponseDTO;
import com.activegym.activegym.model.Users.User;
import com.activegym.activegym.repository.Users.UserRepository;
import com.activegym.activegym.util.AgeCalculator;
import com.activegym.activegym.util.AuxiliarFields;
import com.activegym.activegym.util.ConvertToResponse;
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
    private final ConvertToResponse userResponse;

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
