package org._iir.backend.http;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org._iir.backend.enums.Role;

public record RegistrationRequest(
        @NotBlank(message = "Name cannot be empty") String nom,

        @Email(message = "Email should be valid")
        @NotBlank(message = "Email cannot be empty")
        String email,

        @NotBlank(message = "Password cannot be empty") String password,

        @NotNull(message = "Role cannot be null") Role role) {
    public RegistrationRequest {
        // Ensure the role is not ADMIN
        if (role == Role.ADMIN) {
            throw new IllegalArgumentException("Role cannot be ADMIN for registration");
        }
    }
}

