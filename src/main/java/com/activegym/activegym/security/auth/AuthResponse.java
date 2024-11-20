package com.activegym.activegym.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AuthResponse represents the response sent to the client upon successful authentication.
 * It contains the JWT token and the username of the authenticated user.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    /**
     * The JWT token generated upon successful login.
     */
    String token;

    /**
     * The username of the authenticated user.
     */
    String userName;

    /**
     * The user profile picture
     */
    String profilePicture;
}
