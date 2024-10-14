package com.activegym.activegym.controller.Users;

import com.activegym.activegym.dto.UserDTO;
import com.activegym.activegym.dto.UserResponseDTO;
import com.activegym.activegym.model.Users.User;
import com.activegym.activegym.service.Users.UserService;
import com.activegym.activegym.util.ConvertToResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@AllArgsConstructor
@RequestMapping("api/users")
@RestController
public class UserController {

    private final UserService userService; // Injected by Lombok
    private final ConvertToResponse convertToResponse; // Injected by Lombok

    // Management endpoints (for ADMINISTRADOR and ASESOR roles)

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @GetMapping
    public Iterable<UserResponseDTO> list() {
        return userService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @GetMapping("/{document}")
    public UserResponseDTO get(@PathVariable("document") String document) {
        return userService.findByDocument(document);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserDTO userDTO) {
        User user = userService.create(userDTO);
        UserResponseDTO responseDTO = convertToResponse.convertToResponseDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @PutMapping("/{document}")
    public ResponseEntity<String> updateBasicInfo(@PathVariable("document") String document, @RequestBody UserDTO userDTO) {
        userService.updateBasicInfo(document, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Basic info updated");
    }

    // Role management endpoints (for ADMINISTRADOR role)

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/{document}/roles")
    public ResponseEntity<String> addRole(@PathVariable("document") String document, @RequestBody Map<String, String> request) {
        String roleName = request.get("roleName");
        userService.addRole(document, roleName);
        return ResponseEntity.status(HttpStatus.CREATED).body("Role added to user");
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @DeleteMapping("/{document}/roles")
    public ResponseEntity<String> removeRole(@PathVariable("document") String document, @RequestBody Map<String, String> request) {

        String roleName = request.get("roleName");
        userService.removeRole(document, roleName);
        return ResponseEntity.status(HttpStatus.OK).body("Role removed from user");

    }

    // Other ADMINISTRADOR endpoints
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @DeleteMapping("/{document}")
    public ResponseEntity<String> delete(@PathVariable("document") String document) {
        userService.delete(document);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }

    // User endpoints

}
