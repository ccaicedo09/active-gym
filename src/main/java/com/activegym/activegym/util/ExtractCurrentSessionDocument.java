package com.activegym.activegym.util;

import com.activegym.activegym.model.Users.User;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractCurrentSessionDocument {

    public String extractDocument() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return ((User) principal).getDocument();
        }
        throw new RuntimeException("User not found");
    }
}
