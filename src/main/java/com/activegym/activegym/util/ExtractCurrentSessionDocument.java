package com.activegym.activegym.util;

import com.activegym.activegym.model.Users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Utility class for extracting the document of the current session's user.
 * This class contains the business logic for retrieving the document of the authenticated user.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Service
@RequiredArgsConstructor
public class ExtractCurrentSessionDocument {

    /**
     * <p>Extracts the document of the currently authenticated user using the Spring Security Context Holder.</p>
     *
     * <p>This method uses the getAuthentication method of SecurityContextHolder to retrieve the current session's object, which contains information about the authenticated user and their credentials.</p>
     *
     * <p>If the authenticated user is an instance of {@code UserDetails}, this User is extracted and the document is returned. If user is not found or the type of {@code principal} does not match, an exception is thrown.</p>
     *
     * @return the document of the authenticated user.
     * @throws UsernameNotFoundException if the user is not found.
     */
    public String extractDocument() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return ((User) principal).getDocument();
        }
        throw new UsernameNotFoundException("Usuario no encontrado");
    }
}
