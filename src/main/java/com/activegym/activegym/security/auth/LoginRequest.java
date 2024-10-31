package com.activegym.activegym.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginRequest is the expected body of the login request, containing the login credentials sent by the client to authenticate a user.
 * It contains the email and password of the user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    /**
     * The email of the user attempting to log in.
     */
    String email;

    /**
     * The password of the user attempting to log in.
     */
    String password;
}
