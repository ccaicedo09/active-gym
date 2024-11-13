package com.activegym.activegym.service.Users;

import com.activegym.activegym.dto.users.UserDTO;
import com.activegym.activegym.dto.users.UserFilterCriteriaDTO;
import com.activegym.activegym.dto.users.UserResponseDTO;
import com.activegym.activegym.exceptions.RoleNotFoundException;
import com.activegym.activegym.exceptions.UserNotFoundException;
import com.activegym.activegym.model.Roles.Role;
import com.activegym.activegym.model.Users.User;
import com.activegym.activegym.repository.Roles.RoleRepository;
import com.activegym.activegym.repository.Users.UserRepository;
import com.activegym.activegym.security.auth.AuthService;
import com.activegym.activegym.util.AgeCalculator;
import com.activegym.activegym.util.AuxiliarFields;
import com.activegym.activegym.util.ConvertToResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing user-related operations.
 * This class contains the business logic for creating, updating, deleting, and retrieving users.
 * It also handles role management and password changes.
 *
 * <p> Note that anytime auxiliary fields are used, the Auxiliar Fields casts the DTO fields to the entity fields due to the relationship between both. This is done to facilitate the creation process inserting the string fields. Make sure that anytime you want to interact with these fields, you should firstly fetch the existing resources and give them to choose to the user.</p>
 * @author Carlos Esteban Castro Caicedo
 * @since v1.0
 */
@AllArgsConstructor
@Service
public class UserService {

    /**
     * Import necessary repositories and external services:
     * <ul>
     *     <li> UserRepository for CRUD operations of the User entity.</li>
     *     <li> ModelMapper for mapping DTOs to entities and vice versa.</li>
     *     <li> AuxiliarFields service for casting DTOs' string fields to entities.</li>
     *     <li> AgeCalculator for calculating the age of a user based on their date of birth.</li>
     *     <li> ConvertToResponse for converting User entities to UserResponseDTOs.</li>
     *     <li> PasswordEncoder for encoding passwords.</li>
     *     <li> RoleRepository for CRUD operations of the Role entity.</li>
     *     <li> AuthService for getting the roles of the current session user.</li>
     * </ul>
     */
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final AuxiliarFields auxiliarFields;
    private final AgeCalculator ageCalculator;
    private final ConvertToResponse userResponse;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthService authService;

    /**
     * Retrieves a paginates list of all users (including all roles), filtering by the criteria if provided.
     *
     * @param criteria the criteria to filter the users.
     * @param page     the page number to retrieve.
     * @param size     the number of users per page.
     * @return a paginated list of all users.
     */
    public Page<UserResponseDTO> findAll(UserFilterCriteriaDTO criteria, int page, int size) {

        PageRequest pageable = PageRequest.of(page, size);

        return userRepository.findAll(
                criteria.getDocument(),
                criteria.getName(),
                criteria.getPhone(),
                pageable)
                .map(user -> {
                    ageCalculator.setAge(user);
                    return userResponse.convertToResponseDTO(user);
                });
    }

    /**
     * Finds a paginated list of users with only the "MIEMBRO" role,
     * filtering by the criteria if provided.
     *
     * @param criteria the criteria to filter the users.
     * @param page     the page number to retrieve.
     * @param size     the size of members per page.
     * @return a paginated list of users with only the "MIEMBRO" role.
     */
    public Page<UserResponseDTO> findUsersWithOnlyMemberRole(UserFilterCriteriaDTO criteria, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);

        return userRepository.findUsersWithOnlyMemberRole(
                criteria.getDocument(),
                criteria.getName(),
                criteria.getPhone(),
                pageable)
                .map(user -> {
                    ageCalculator.setAge(user);
                    return userResponse.convertToResponseDTO(user);
                });
    }

    /**
     * Gets a list of team members by their roles. This method is intended to be used by the admin users.
     *
     * @return a list of team members with roles "ADMINISTRADOR", "ASESOR", "ENTRENADOR", and "PERSONAL DE ASEO".
     */
    public List<UserResponseDTO> getTeamMembersByRoles() {
        List<String> roles = List.of("ADMINISTRADOR", "ASESOR", "ENTRENADOR", "PERSONAL DE ASEO");
        return userRepository.findByRolesIn(roles).stream()
                .map(userResponse::convertToResponseDTO)
                .toList();
    }

    /**
     * Finds a user by their document.
     *
     * @param document the document of the user to retrieve.
     * @return the user cast into a UserResponseDTO.
     * @throws AccessDeniedException if the requesting user does not have permission to access the data.
     * @throws UserNotFoundException if the user is not found.
     */
    public UserResponseDTO findByDocument(String document) throws AccessDeniedException {
        User user = userRepository
                .findByDocument(document)
                .orElseThrow(() -> new UserNotFoundException("Member not found"));

        List<String> requestUserRoles = authService.getUserRoles();
        List<String> targetUserRoles = user.getRoles().stream()
                .map(Role::getRoleName)
                .toList();
        List<String> restrictedRoles = List.of("ADMINISTRADOR", "ASESOR", "ENTRENADOR", "PERSONAL DE ASEO");

        if (!requestUserRoles.contains("ADMINISTRADOR") && targetUserRoles.stream().anyMatch(restrictedRoles::contains)) {
            throw new AccessDeniedException("");
        }
        ageCalculator.setAge(user);
        return userResponse.convertToResponseDTO(user);
    }

    /**
     * Create a new user.
     *
     * @param userDTO the user DTO containing the user information.
     * @return the created user with a default password (their document) and the role "MIEMBRO".
     * @throws RoleNotFoundException if the role "MIEMBRO" is not found.
     */
    public User create(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);

        auxiliarFields.castUserAuxiliarFields(userDTO, user);

        String defaultPassword = userDTO.getDocument();

        LocalDate now = LocalDate.now();
        user.setCreatedAt(now);

        user.setPassword(passwordEncoder.encode(defaultPassword)); // Default password, should be changed by User
        Role defaultRole = roleRepository.findByRoleName("MIEMBRO")
                .orElseThrow(() -> new RoleNotFoundException(""));
        user.getRoles().add(defaultRole);

        return userRepository.save(user);
    }

// Development use only: anytime a new admin user is needed, this method can be used to create one.
//    public User createAdmin(AdminDTO adminDTO) {
//        User user = mapper.map(adminDTO, User.class);
//        auxiliarFields.castAdminAuxiliarFields(adminDTO, user);
//        user.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
//        Role defaultRole = roleRepository.findByRoleName("ADMINISTRADOR")
//                .orElseThrow(() -> new RuntimeException("Role not found"));
//        user.getRoles().add(defaultRole);
//        return userRepository.save(user);
//    }

    /**
     * Update basic information of a user.
     *
     * @param document the document of the user to update.
     * @param userDTO  the user DTO containing the updated information.
     * @throws UserNotFoundException if the user is not found.
     */
    public void updateBasicInfo(String document, UserDTO userDTO) {
        User user = userRepository
                .findByDocument(document)
                .orElseThrow(() -> new UserNotFoundException(""));

        if (userDTO.getDocument() != null) user.setDocument(userDTO.getDocument());
        if (userDTO.getFirstName() != null) user.setFirstName(userDTO.getFirstName());
        if (userDTO.getLastName() != null) user.setLastName(userDTO.getLastName());
        if (userDTO.getPhone() != null) user.setPhone(userDTO.getPhone());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getDateOfBirth() != null) user.setDateOfBirth(userDTO.getDateOfBirth());

        auxiliarFields.castUserAuxiliarFields(userDTO, user);

        userRepository.save(user);
    }

    /**
     * Deletes a user by their document.
     *
     * @param document the document of the user to delete.
     * @deprecated This method is not recommended for use in production environment.
     */
    public void delete(String document) {
        User user = userRepository
                .findByDocument(document)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        userRepository.delete(user);
    }

    /**
     * Add a role to a user.
     *
     * @param document the document of the user.
     * @param roleName the name of the rol to add.
     * @throws UserNotFoundException if the user is not found.
     * @throws RoleNotFoundException if the role is not found.
     */
    public void addRole(String document, String roleName) {
        User user = userRepository
                .findByDocument(document)
                .orElseThrow(() -> new UserNotFoundException(""));
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(""));

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
        }

        userRepository.save(user);
    }

    /**
     * Removes a role from a user.
     *
     * @param document the document of the user.
     * @param roleName the name of the role to remove.
     * @throws UserNotFoundException if the user is not found.
     * @throws RoleNotFoundException if the role is not found.
     */
    public void removeRole(String document, String roleName) {
        User user = userRepository
                .findByDocument(document)
                .orElseThrow(() -> new UserNotFoundException(""));
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(""));

        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
        }

        userRepository.save(user);
    }

    /**
     * Changes the password of a user by and admin.
     *
     * @param document    the document of the user.
     * @param newPassword the new password to set.
     * @since v1.2
     * @throws UserNotFoundException if the user is not found.
     */
    public void adminChangePassword(String document, String newPassword) {
        User user = userRepository
                .findByDocument(document)
                .orElseThrow(() -> new UserNotFoundException(""));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Changes the password of a user.
     * @since v1.2
     * @param document    the document of the user.
     * @param oldPassword the old password of the user.
     * @param newPassword the new password to set.
     * @throws BadCredentialsException if the old password is incorrect.
     * @throws UserNotFoundException if the user is not found.
     */
    public void userChangePassword(String document, String oldPassword, String newPassword) {
        User user = userRepository
                .findByDocument(document)
                .orElseThrow(() -> new UserNotFoundException(""));
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new BadCredentialsException("");
        }
    }

    /**
     * Counts the number of users created in the last week.
     * @return the number of users created in the last week.
     */
    public Long countUsersCreatedLastWeek() {
        return userRepository.countUsersCreatedInLastWeek();
    }
}
