package com.activegym.activegym.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthFilter is an authentication filter that intercepts HTTP requests
 * to verify and validate JWT tokens. This filter is responsible for authenticating the
 * user based on the token present in the request's authorization header.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Filters the HTTP request, extracting the JWT token and verifying its validity.
     *
     * <ol>
     *     <li>Checks if there is no token in the request. If not, it passes the request and response to the
     *     next filter in the chain and returns immediately, meaning this filter does not perform any authentication.
     *     </li>
     *     <li>
     *         As the token is present in the request, it extracts the username from the token.
     *     </li>
     *     <li>
     *         Checks if there is and existing authentication for that username. This prevents the re-authenticating
     *         the same user multiple times for the same request.
     *     </li>
     *     <li>
     *         If both conditions are met, user details are loaded from the {@code UserDetailsService}.
     *     </li>
     *     <li>
     *         The validity of the token is checked using the {@link JwtService} {@code isTokenValid} method. If it is
     *         valid, it means the token has not expired and is associated with the provided user details. If
     *         valid, creates a new {@link UsernamePasswordAuthenticationToken} object named authToken.
     *     </li>
     *     <li>
     *         Regardless the token was valid or not, the request proceeds down the filter.
     *     </li>
     * </ol>
     *
     * @param request  the HTTP request being processed.
     * @param response the HTTP response to be sent.
     * @param filterChain the filter chain to continue processing after this filter.
     * @throws ServletException if an error occurs during request processing.
     * @throws IOException      if an input/output error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = getTokenFromRequest(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtService.getUsernameFromToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        filterChain.doFilter(request, response);

    }

    /**
     * Extracts the JWT token from the request's authorization header.
     * @param request the HTTP request from which the token is extracted.
     * @return the JWT token extracted from the request or null if no token is present.
     */
    private String getTokenFromRequest(HttpServletRequest request) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
