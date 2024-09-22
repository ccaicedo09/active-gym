package com.activegym.activegym.Controllers;

import com.activegym.activegym.DTO.UserDTO;
import com.activegym.activegym.DTO.UserResponseDTO;
import com.activegym.activegym.Entities.User;
import com.activegym.activegym.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin // TEMPORAL FEATURE
@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService; // Injected by Lombok

    @GetMapping
    public Iterable<UserResponseDTO> list() {
        return userService.findAll();
    }

    @GetMapping("/{document}")
    public UserResponseDTO get(@PathVariable("document") String document) {
        return userService.findByDocument(document);
    }

    @PostMapping
    public User create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PutMapping("/{document}")
    public User update(@PathVariable("document") String document, @RequestBody UserDTO userDTO) {
        return userService.update(document, userDTO);
    }
}
