package com.activegym.activegym.Controllers;

import com.activegym.activegym.DTO.UserDTO;
import com.activegym.activegym.Entities.User;
import com.activegym.activegym.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService; // Injected by Lombok

    @GetMapping
    public Iterable<User> list() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public User create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

}
