package com.activegym.activegym.controller.Users;

import com.activegym.activegym.dto.ResponseStatusMessage;
import com.activegym.activegym.dto.users.UserDTO;
import com.activegym.activegym.dto.users.UserFilterCriteriaDTO;
import com.activegym.activegym.dto.users.UserResponseDTO;
import com.activegym.activegym.model.Users.User;
import com.activegym.activegym.security.auth.AuthService;
import com.activegym.activegym.service.Users.UserService;
import com.activegym.activegym.util.ConvertToResponse;
import com.activegym.activegym.util.ExtractCurrentSessionDocument;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
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

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "https://activegym.vercel.app/"})
@AllArgsConstructor
@RequestMapping("api/users")
@RestController
@Tag(name = "Users Controller", description = "Endpoints for managing users, including user creation, updates, role management, password changes, and retrieving user information.")

@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "Access denied. The user does not have permission to access this resource."),
        @ApiResponse(responseCode = "500", description = "Internal server error. An unexpected error occurred.")
})
public class UserController {

    private final UserService userService;
    private final ConvertToResponse convertToResponse;
    private final ResponseStatusMessage responseStatusMessage;
    private final AuthService authService;
    private final ExtractCurrentSessionDocument extractCurrentSessionDocument;

    // Management endpoints (for ADMINISTRADOR and ASESOR roles)
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR', 'ENTRENADOR')")
    @GetMapping
    @Operation(summary = "MANAGEMENT: List all users", description = "List all users with pagination. If the user is ADMINISTRADOR, all users are listed. If the user is ASESOR, only users with the MIEMBRO role are listed. This list of users can be filtered by document, name, and phone.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No users found. There are no users in the system matching the criteria.")
    })
    public ResponseEntity<Page<UserResponseDTO>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String document,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone) {

        UserFilterCriteriaDTO criteria = UserFilterCriteriaDTO.builder()
                .document(document)
                .name(name)
                .phone(phone)
                .build();

        if (authService.getUserRoles().contains("ADMINISTRADOR")) {
            return ResponseEntity.ok(userService.findAll(criteria, page, size));
        } else {
            return ResponseEntity.ok(userService.findUsersWithOnlyMemberRole(criteria, page, size));
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @GetMapping("/{document}")
    @Operation(summary = "MANAGEMENT: Get user by document", description = "Get user by their document, authorized for ADMINISTRADOR and ASESOR roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found. No user exists with the provided document.")
    })
    public UserResponseDTO get(@PathVariable("document") String document) throws AccessDeniedException {
        return userService.findByDocument(document);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @PostMapping
    @Operation(summary = "MANAGEMENT: Create user", description = "Create a new user expecting a UserDTO body, authorized for ADMINISTRADOR and ASESOR roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input. The provided UserDTO is not valid."),
            @ApiResponse(responseCode = "409", description = "Conflict. The user with the given document already exists.")
    })
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserDTO userDTO) {
        User user = userService.create(userDTO);
        UserResponseDTO responseDTO = convertToResponse.convertToResponseDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @PutMapping("/{document}")
    @Operation(summary = "MANAGEMENT: Update user", description = "Update user by their document, expecting a UserDTO body, authorized for ADMINISTRADOR and ASESOR roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User basic information updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input. The provided UserDTO contains invalid data."),
            @ApiResponse(responseCode = "404", description = "User not found. No user exists with the given document."),
            @ApiResponse(responseCode = "409", description = "Conflict. Some of the unique constrained inputs are already in use by another user.")
    })
    public ResponseEntity<ResponseStatusMessage> updateBasicInfo(@PathVariable("document") String document, @RequestBody UserDTO userDTO) {
        userService.updateBasicInfo(document, userDTO);
        responseStatusMessage.setMessage("Basic info updated");
        return ResponseEntity.status(HttpStatus.OK).body(responseStatusMessage);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @GetMapping("/recent-count")
    @Operation(summary = "MANAGEMENT: Get recent users count", description = "Get the count of users created in the last 30 days, authorized for ADMINISTRADOR and ASESOR roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the count of recent users")
    })
    public ResponseEntity<Long> getRecentUsersCount() {
        return ResponseEntity.ok(userService.countUsersCreatedLastWeek());
    }

    // Role management endpoints (for ADMINISTRADOR role)

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/{document}/roles")
    @Operation(summary = "ADMIN: Add role to user", description = "Add a role to a user by their document, expecting a roleName in the body, authorized for ADMINISTRADOR role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role added to user successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input. Role name may be missing or invalid."),
            @ApiResponse(responseCode = "404", description = "User not found or role not found.")
    })
    public ResponseEntity<ResponseStatusMessage> addRole(@PathVariable("document") String document, @RequestBody Map<String, String> request) {
        String roleName = request.get("roleName");
        userService.addRole(document, roleName);
        responseStatusMessage.setMessage("Role added to user");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseStatusMessage);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @DeleteMapping("/{document}/roles")
    @Operation(summary = "ADMIN: Remove role from user", description = "Remove a role from a user by their document, expecting a roleName in the body, authorized for ADMINISTRADOR role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role removed from user successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input. Role name may be missing or invalid."),
            @ApiResponse(responseCode = "404", description = "User not found or role not found.")
    })
    public ResponseEntity<ResponseStatusMessage> removeRole(@PathVariable("document") String document, @RequestBody Map<String, String> request) {
        String roleName = request.get("roleName");
        userService.removeRole(document, roleName);
        responseStatusMessage.setMessage("Role removed from user");
        return ResponseEntity.status(HttpStatus.OK).body(responseStatusMessage);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/team")
    @Operation(summary = "ADMIN: Get team members", description = "Get all team members by roles (including all roles but MIEMBRO, authorized for ADMINISTRADOR role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team members retrieved successfully", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    })
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

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PutMapping("/{document}/change-password")
    @Operation(summary = "ADMIN: Change user password", description = "Change user password by their document, expecting a password in the body, authorized for ADMINISTRADOR role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input. The provided password does not meet requirements."),
            @ApiResponse(responseCode = "404", description = "User not found. No user exists with the provided document.")
    })
    public ResponseEntity<ResponseStatusMessage> changePassword(@PathVariable("document") String document, @RequestBody Map<String, String> request) {
        String password = request.get("password");
        userService.adminChangePassword(document, password);
        responseStatusMessage.setMessage("Password changed");
        return ResponseEntity.status(HttpStatus.OK).body(responseStatusMessage);
    }

    // User & self-management endpoints
    @PutMapping("/change-password")
    @Operation(summary = "Change user password", description = "Change user password, expecting a password in the body, authorized for all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input. The provided passwords do not meet the requirements."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. The old password is incorrect.")
    })
    public ResponseEntity<ResponseStatusMessage> changePassword(@RequestBody Map<String, String> request) {
        String document = extractCurrentSessionDocument.extractDocument();
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        userService.userChangePassword(document, oldPassword, newPassword);
        responseStatusMessage.setMessage("Password changed");
        return ResponseEntity.status(HttpStatus.OK).body(responseStatusMessage);
    }

    @GetMapping("self-management/get-info")
    @Operation(summary = "SELF-MANAGEMENT: Get logged user", description = "Gets the currently logged user's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found. No user exists with the provided document.")
    })
    public UserResponseDTO getLoggedUserInfo() throws AccessDeniedException {
        String document = extractCurrentSessionDocument.extractDocument();
        return userService.findByDocument(document);
    }

    @PutMapping("self-management/update-info")
    @Operation(summary = "SELF-MANAGEMENT: Update basic information", description = "Update currently logged user by extracting their document from the session, expecting a UserDTO body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User basic information updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input. The provided UserDTO contains invalid data."),
            @ApiResponse(responseCode = "404", description = "User not found. No user exists with the given document."),
            @ApiResponse(responseCode = "409", description = "Conflict. Some of the unique constrained inputs are already in use by another user.")
    })
    public ResponseEntity<ResponseStatusMessage> updateBasicInfo(@RequestBody UserDTO userDTO) {
        String document = extractCurrentSessionDocument.extractDocument();
        userService.updateBasicInfo(document, userDTO);
        responseStatusMessage.setMessage("Basic info updated");
        return ResponseEntity.status(HttpStatus.OK).body(responseStatusMessage);
    }

}
