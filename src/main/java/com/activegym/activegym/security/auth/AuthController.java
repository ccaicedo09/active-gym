package com.activegym.activegym.security.auth;

import com.activegym.activegym.security.jwt.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AuthController handles authentication-related requests such as login,
 * token verification, and retrieving user roles.
 * @since v1.0
 * @author Carlos Esten Castro Caicedo
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://activegym.vercel.app/"})
@Tag(name = "Auth", description = "Authentication management")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * Handles user login by validating credentials and generating a JWT token.
     *
     * @param request the {@link LoginRequest} containing the user's email and password.
     * @return a {@link ResponseEntity} containing the {@link AuthResponse} with the JWT token.
     */
    @PostMapping("/login")
    @Operation(summary = "AUTH: Login", description = "Login with username and password")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * Verifies if the provided JWT token is valid and not expired.
     *
     * @param tokenRequest the {@link TokenRequest} containing the JWT token to be verified.
     * @return {@code true} if the token is valid, {@code false} otherwise.
     */
    @PostMapping("/verify-token")
    @Operation(summary = "AUTH: Verify token", description = "Verify if a token is valid")
    public boolean verifyToken(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        return !jwtService.isTokenExpired(token);
    }

    /**
     * Fetches the roles (authorities) of the currently authenticated user.
     *
     * @return a list of role names as {@link String}.
     */
    @GetMapping("/roles")
    @Operation(summary = "AUTH: Get user roles", description = "Fetch the roles of the current user")
    public List<String> getUserRoles() {
        return authService.getUserRoles();
    }

}
