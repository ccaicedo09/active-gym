package com.activegym.activegym.controller.Users;

import com.activegym.activegym.dto.UserDTO;
import com.activegym.activegym.dto.UserResponseDTO;
import com.activegym.activegym.model.Users.User;
import com.activegym.activegym.service.Users.UserService;
import com.activegym.activegym.util.ConvertToResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ConvertToResponse convertToResponse; // Injected by Lombok

    @GetMapping
    public Iterable<UserResponseDTO> list() {
        return userService.findAll();
    }

    @GetMapping("/{document}")
    public UserResponseDTO get(@PathVariable("document") String document) {
        return userService.findByDocument(document);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserDTO userDTO) {
        User user = userService.create(userDTO);
        UserResponseDTO responseDTO = convertToResponse.convertToResponseDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{document}/basic-info")
    public ResponseEntity<String> updateBasicInfo(@PathVariable("document") String document, @RequestBody UserDTO userDTO) {
        userService.updateBasicInfo(document, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Basic info updated");
    }
}
