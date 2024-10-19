package com.activegym.activegym.security.auth;

import com.activegym.activegym.repository.Users.UserRepository;
import com.activegym.activegym.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        String token = jwtService.getToken(user);

        String userName = jwtService.extractUserName(token);

        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        ResponseCookie rolesCookie = ResponseCookie.from("user_roles", String.join("|", authorities))
                .httpOnly(true)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, rolesCookie.toString())
                .body(AuthResponse.builder()
                        .token(token)
                        .userName(userName)
                        .build());
    }
}
