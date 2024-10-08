package com.activegym.activegym.service.Users;

import com.activegym.activegym.dto.UserDTO;
import com.activegym.activegym.dto.UserResponseDTO;
import com.activegym.activegym.model.Roles.Role;
import com.activegym.activegym.model.Users.User;
import com.activegym.activegym.repository.Roles.RoleRepository;
import com.activegym.activegym.repository.Users.UserRepository;
import com.activegym.activegym.util.AgeCalculator;
import com.activegym.activegym.util.AuxiliarFields;
import com.activegym.activegym.util.ConvertToResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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

        String defaultPassword = userDTO.getDocument();

        user.setPassword(passwordEncoder.encode(defaultPassword)); // Default password, should be changed by User
        Role defaultRole = roleRepository.findByRoleName("MIEMBRO")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(defaultRole);

        return userRepository.save(user);
    }

    public void updateBasicInfo(String document, UserDTO userDTO) {
        User user = userRepository
                .findByDocument(document)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (userDTO.getDocument() != null) user.setDocument(userDTO.getDocument());
        if (userDTO.getFirstName() != null) user.setFirstName(userDTO.getFirstName());
        if (userDTO.getLastName() != null) user.setLastName(userDTO.getLastName());
        if (userDTO.getPhone() != null) user.setPhone(userDTO.getPhone());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getDateOfBirth() != null) user.setDateOfBirth(userDTO.getDateOfBirth());

        auxiliarFields.castUserAuxiliarFields(userDTO, user);

        userRepository.save(user);
    }
}