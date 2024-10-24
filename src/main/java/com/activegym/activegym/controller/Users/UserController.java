package com.activegym.activegym.controller.Users;

import com.activegym.activegym.dto.AdminDTO;
import com.activegym.activegym.dto.ResponseStatusMessage;
import com.activegym.activegym.dto.UserDTO;
import com.activegym.activegym.dto.UserResponseDTO;
import com.activegym.activegym.model.Users.User;
import com.activegym.activegym.service.Users.UserService;
import com.activegym.activegym.util.ConvertToResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "https://activegym.vercel.app/"})
@AllArgsConstructor
@RequestMapping("api/users")
@RestController
@Tag(name = "Users", description = "User management")
public class UserController {

    private final UserService userService; // Injected by Lombok
    private final ConvertToResponse convertToResponse; // Injected by Lombok
    private final ResponseStatusMessage responseStatusMessage; // Injected by Lombok

    // Management endpoints (for ADMINISTRADOR and ASESOR roles)

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @GetMapping
    @Operation(summary = "MANAGEMENT: List all users", description = "List all users with pagination included, authorized for ADMINISTRADOR and ASESOR roles")
    public ResponseEntity<Page<UserResponseDTO>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserResponseDTO> users = userService.findAll(page, size);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @GetMapping("/{document}")
    @Operation(summary = "MANAGEMENT: Get user by document", description = "Get user by their document, authorized for ADMINISTRADOR and ASESOR roles")
    public UserResponseDTO get(@PathVariable("document") String document) {
        return userService.findByDocument(document);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @PostMapping
    @Operation(summary = "MANAGEMENT: Create user", description = "Create a new user expecting a UserDTO body, authorized for ADMINISTRADOR and ASESOR roles")
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserDTO userDTO) {
        User user = userService.create(userDTO);
        UserResponseDTO responseDTO = convertToResponse.convertToResponseDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @PutMapping("/{document}")
    @Operation(summary = "MANAGEMENT: Update user", description = "Update user by their document, expecting a UserDTO body, authorized for ADMINISTRADOR and ASESOR roles")
    public ResponseEntity<ResponseStatusMessage> updateBasicInfo(@PathVariable("document") String document, @RequestBody UserDTO userDTO) {
        userService.updateBasicInfo(document, userDTO);
        responseStatusMessage.setMessage("Basic info updated");
        return ResponseEntity.status(HttpStatus.OK).body(responseStatusMessage);
    }

    // Role management endpoints (for ADMINISTRADOR role)

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/{document}/roles")
    @Operation(summary = "ADMIN: Add role to user", description = "Add a role to a user by their document, expecting a roleName in the body, authorized for ADMINISTRADOR role")
    public ResponseEntity<ResponseStatusMessage> addRole(@PathVariable("document") String document, @RequestBody Map<String, String> request) {
        String roleName = request.get("roleName");
        userService.addRole(document, roleName);
        responseStatusMessage.setMessage("Role added to user");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseStatusMessage);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @DeleteMapping("/{document}/roles")
    @Operation(summary = "ADMIN: Remove role from user", description = "Remove a role from a user by their document, expecting a roleName in the body, authorized for ADMINISTRADOR role")
    public ResponseEntity<ResponseStatusMessage> removeRole(@PathVariable("document") String document, @RequestBody Map<String, String> request) {

        String roleName = request.get("roleName");
        userService.removeRole(document, roleName);
        responseStatusMessage.setMessage("Role removed from user");
        return ResponseEntity.status(HttpStatus.OK).body(responseStatusMessage);

    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/team")
    @Operation(summary = "ADMIN: Get team members", description = "Get all team members by roles (including all roles but MIEMBRO, authorized for ADMINISTRADOR role")
    public ResponseEntity<List<UserResponseDTO>> getTeamMembers() {
        List<UserResponseDTO> teamMembers = userService.getTeamMembersByRoles();
        return ResponseEntity.ok(teamMembers);
    }

    // Other ADMINISTRADOR endpoints
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @DeleteMapping("/{document}")
    @Operation(summary = "ADMIN: Delete user", description = "(NOT WORKING YET) Delete user by their document, authorized for ADMINISTRADOR role")
    public ResponseEntity<ResponseStatusMessage> delete(@PathVariable("document") String document) {
        userService.delete(document);
        responseStatusMessage.setMessage("User deleted");
        return ResponseEntity.status(HttpStatus.OK).body(responseStatusMessage);
    }

//    @PostMapping("/public/create-admin")
//    public ResponseEntity<UserResponseDTO> createAdmin(@RequestBody AdminDTO adminDTO) {
//        User admin = userService.createAdmin(adminDTO);
//        UserResponseDTO responseDTO = convertToResponse.convertToResponseDTO(admin);
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
//    }

    // User endpoints

}
