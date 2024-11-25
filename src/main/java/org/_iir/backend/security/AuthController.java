package org._iir.backend.security;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    // Public endpoint (accessible without authentication)
    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint. No authentication required.";
    }

    // Secured endpoint (requires authentication)
    @GetMapping("/secure")
    public String secureEndpoint() {
        return "This is a secured endpoint. Authentication is required.";
    }

    // Role-based secured endpoint
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "This is an admin endpoint. Only users with the ADMIN role can access this.";
    }

}
