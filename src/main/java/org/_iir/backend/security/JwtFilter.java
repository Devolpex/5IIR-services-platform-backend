package org._iir.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor; // For @RequiredArgsConstructor
import org.springframework.stereotype.Component; // For @Component
import org.springframework.web.filter.OncePerRequestFilter; // Base class for the filter
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; // For HTTP servlet responses
import java.io.IOException; // For handling IO exceptions
import org.springframework.security.core.userdetails.UserDetailsService; // Interface for user details service
import org.springframework.security.core.userdetails.UserDetails; // Interface for user details

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // For authentication tokens
import org.springframework.security.core.context.SecurityContextHolder; // To manage the SecurityContext
import org.springframework.security.core.userdetails.UserDetails; // Interface for user details
import org.springframework.security.core.userdetails.UserDetailsService; // Interface for user details service
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; // For authentication details

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            logger.debug("Processing authentication for '{}'" + request.getRequestURL());

            // Check if the Token exist in the header of request
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String useremail;
            String path = request.getRequestURI();

            // Bypass JWT validation for public endpoints
            if (isPublicPath(path)) {
                filterChain.doFilter(request, response);
                return;
            }
            this.logger.info("Path: '{}'" + path);

            // Logger the auth header
            logger.info("Auth header: '{}'" + authHeader);
            // Logger the jwt
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7);
            logger.info("JWT: '{}'" + jwt);

            useremail = jwtProvider.extractUserEmail(jwt);
            if (useremail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Get the user details from the database
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(useremail);

                // Logger the user details
                logger.info("User details: '{}'" + userDetails);

                // Validate the token
                if (jwtProvider.validateToken(jwt, userDetails)) {
                    // Create the authentication token
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

                    // Set the authentication details
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);

        }

        filterChain.doFilter(request, response);
    }

    // Method to determine public paths
    private boolean isPublicPath(String path) {
        return path.startsWith("/auth") || path.startsWith("/public");
    }
}
