package com.activegym.activegym.Services;


import com.activegym.activegym.DTO.UserDTO;
import com.activegym.activegym.DTO.UserResponseDTO;
import com.activegym.activegym.Entities.BloodRh;
import com.activegym.activegym.Entities.BloodType;
import com.activegym.activegym.Entities.Eps;
import com.activegym.activegym.Entities.Gender;
import com.activegym.activegym.Entities.User;
import com.activegym.activegym.Repositories.BloodRhRepository;
import com.activegym.activegym.Repositories.BloodTypeRepository;
import com.activegym.activegym.Repositories.EpsRepository;
import com.activegym.activegym.Repositories.GenderRepository;
import com.activegym.activegym.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UserService {

    @Autowired
    private EpsRepository epsRepository;
    @Autowired
    private BloodTypeRepository bloodTypeRepository;
    @Autowired
    private BloodRhRepository bloodRhRepository;
    @Autowired
    private GenderRepository genderRepository;

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public Iterable<UserResponseDTO> findAll() {
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        users.forEach(this::setAge);
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserResponseDTO findByDocument(String document) {
        User user = userRepository
                .findByDocument(document)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        setAge(user);
        return convertToDTO(user);
    }

    public User create(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);

        castAuxiliarFields(userDTO, user);

        user.setPassword(userDTO.getDocument()); // Default password, should be changed by User

        return userRepository.save(user);
    }

    public User update(String document, UserDTO userDTO) {
        User user = userRepository
                .findByDocument(document)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        castAuxiliarFields(userDTO, user);

        return userRepository.save(user);
    }

    private void castAuxiliarFields(UserDTO userDTO, User user) {
        Eps eps = epsRepository.findByEpsName(userDTO.getEps())
                .orElseThrow(() -> new RuntimeException("EPS no encontrado"));
        BloodType bloodType = bloodTypeRepository.findByBloodTypeName(userDTO.getBloodType())
                .orElseThrow(() -> new RuntimeException("Tipo de sangre no encontrado"));
        BloodRh bloodRh = bloodRhRepository.findByBloodRh(userDTO.getBloodRh())
                .orElseThrow(() -> new RuntimeException("Factor Rh no encontrado"));
        Gender gender = genderRepository.findByGenderName(userDTO.getGender())
                .orElseThrow(() -> new RuntimeException("Género no encontrado"));

        user.setEps(eps);
        user.setBloodType(bloodType);
        user.setBloodRh(bloodRh);
        user.setGender(gender);
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
