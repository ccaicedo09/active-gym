package com.activegym.activegym.Services;


import com.activegym.activegym.DTO.UserDTO;
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

    public Iterable<User> findAll() {
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        users.forEach(this::setAge);
        return users;
    }

    public User findById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        setAge(user);
        return user;
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

}
