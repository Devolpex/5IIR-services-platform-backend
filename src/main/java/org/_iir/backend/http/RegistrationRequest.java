package org._iir.backend.http;

import org._iir.backend.enums.Role;

public record RegistrationRequest(
        String username,
        String password,
        String email,
        Role role
) {
}
