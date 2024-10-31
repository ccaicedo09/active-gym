package com.activegym.activegym.security.jwt;

import com.activegym.activegym.model.Users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class for handling JWT (JSON Web Token) operations.
 */
@Service
public class JwtService {

    private final String secretKey;


    /**
     * Constructs a JwtService with the provided secret key in the {@link com.activegym.activegym.security.config.ApplicationConfig} method using the {@link io.github.cdimascio.dotenv.Dotenv} bean.
     *
     * @param secretKey the secret key used for signing JWTs.
     */
    @Autowired
    public JwtService(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Generates a JWT for the specified user with default claims.
     *
     * @param user the user details for whom the token is generated.
     * @return the generated JWT as a String.
     */
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    /**
     * Generates a JWT for the specified user with additional claims.
     *
     * @param extraClaims additional claims to be included in the JWT.
     * @param user        the user details for whom the token is generated.
     * @return the generated JWT as a String.
     */
    private String getToken(Map<String,Object> extraClaims, UserDetails user) {

        User customUser = (User) user;
        String document = customUser.getDocument();
        String userName = customUser.getFirstName();

        return Jwts
                .builder()
                .claims(extraClaims)
                .claim("document", document)
                .claim("userName", userName)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Retrieves the signing key used for signing the JWT.
     *
     * @return the SecretKey used for signing.
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts the username from the specified JWT.
     *
     * @param token the JWT from which to extract the username.
     * @return the username extracted from the token.
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Validates the specified JWT against the provided user details.
     *
     * @param token       the JWT to validate.
     * @param userDetails the user details to compare against.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Retrieves all claims from the specified JWT.
     *
     * @param token the JWT from which to extract claims.
     * @return the claims extracted from the token.
     */
    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extracts the username from the specified JWT.
     *
     * @param token the JWT from which to extract the username.
     * @return the username extracted from the token.
     */
    public String extractUserName(String token) {
        return getAllClaims(token).get("userName", String.class);
    }

    /**
     * Retrieves a specific claim from the specified JWT.
     *
     * @param token          the JWT from which to extract the claim.
     * @param claimsResolver a function to resolve the desired claim from the claims.
     * @param <T>           the type of the claim.
     * @return the resolved claim value.
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves the expiration date from the specified JWT.
     *
     * @param token the JWT from which to extract the expiration date.
     * @return the expiration date of the token.
     */
    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Checks whether the specified JWT has expired.
     *
     * @param token the JWT to check.
     * @return true if the token is expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
