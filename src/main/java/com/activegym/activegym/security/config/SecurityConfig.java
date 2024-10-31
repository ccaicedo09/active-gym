package com.activegym.activegym.security.config;

import com.activegym.activegym.security.jwt.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * SecurityConfig defines the security settings for the application, including
 * authentication, authorization, CORS, and session management policies.
 * <ul>
 *     <li>{@code Configuration}annotation for marking the class as a configuration class for Spring.</li>
 *     <li>{@code EnableMethodSecurity} annotation for allowing method-level security using annotations like {@code PreAuthroize} and {@code PostAuthorize}.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;
    /**
     * Configures the security filter chain for handling HTTP requests.
     * <ul>
     *     <li>{@code csrf} disables CSRF protection due to JWT implementation:
     *          <ul>
     *              <li>JWT uses headers and not cookies, reducing the risk of CSRF attacks.</li>
     *              <li>Requests using JWT require explicit authentication instead of cookie-based automatic basic
     *              authentication</li>
     *              <li>JWT is stateless, so there is no need to store session information on the server.</li>
     *          </ul>
     *     </li>
     *     <li>
     *         {@code cors} configures the CORS policy for the application, allowing the mentioned origins, methods,
     *         headers and credentials.
     *     </li>
     *     <li>
     *         {@code authorizeHttpRequests} configures the authorization rules for the application defining which
     *         patterns are public and secures any other request. No patterns is authorized for any role due to
     *         method-level authorization.
     *     </li>
     *     <li>
     *         {@code sessionManagement} configures the session management policy for the application, setting the
     *         session creation policy to stateless for JWT-based authentication.
     *     </li>
     *     <li>
     *         {@code authenticationProvider} sets the authentication provider for the application.
     *     </li>
     *     <li>
     *         {@code addFilterBefore} adds the {@link JwtAuthFilter} before the {@code UsernamePasswordAuthenticationFilter}
     *     </li>
     * </ul>
     * @param http the {@link HttpSecurity} object used to configure security rules.
     * @return the configured {@link SecurityFilterChain}.
     * @throws Exception if an error occurs while building the security chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200", "https://activegym.vercel.app/"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui/index.html").permitAll()
                                .requestMatchers("/api/*/public/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
