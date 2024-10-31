package com.activegym.activegym.security.config;

import com.activegym.activegym.exceptions.UserNotFoundException;
import com.activegym.activegym.repository.Users.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ApplicationConfig defines the application's configuration beans, including security, password encoding, user management and API documentation settings. Also, it enables scheduling for the application.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class ApplicationConfig {

    private final UserRepository userRepository;
    private final Dotenv dotenv = Dotenv.load();

    /**
     * Provides an {@link AuthenticationManager} using the specified {@link AuthenticationConfiguration}.
     *
     * @param config the {@link AuthenticationConfiguration} containing the authentication manager setup.
     * @return the configured {@link AuthenticationManager}.
     * @throws Exception if the authentication manager cannot be created.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configures the {@link AuthenticationProvider} to use a DAO-based authentication mechanism. It uses the {@link UserDetailsService} to retrieve user information and the {@link PasswordEncoder} to encode and verify passwords.
     *
     * @return the configured {@link DaoAuthenticationProvider}.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Provides a {@link PasswordEncoder} using the BCrypt hashing algorithm.
     *
     * @return a new instance of {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the {@link UserDetailsService} to load user details from the {@link UserRepository}.
     *
     * @return the {@link UserDetailsService} implementation.
     * @throws UserNotFoundException if the user is not found in the database.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(""));
    }

    /**
     * Configures an OpenAPI instance to generate API documentation.
     *
     * @return the configured {@link OpenAPI} instance with custom API metadata.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ACTIVE GYM API")
                        .version("1.0")
                        .description("Gym management system")
                        .termsOfService("http://swagger.io/terms/"));
    }

    /**
     * Provides the secret key used to sign and verify JWT tokens.
     * @return the secret key as a {@link String}.
     */
    @Bean
    public String secretKey() {
        return dotenv.get("SECRET_KEY_JWT");
    }
}
