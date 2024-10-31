package com.activegym.activegym.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request containing a JWT token, used for verifying the token validity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {

    /**
     * The JWT token.
     */
    private String token;
}
