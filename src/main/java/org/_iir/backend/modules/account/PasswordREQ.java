package org._iir.backend.modules.account;

import jakarta.validation.constraints.NotBlank;

public record PasswordREQ(
    @NotBlank(message = "Old password is required")
    String oldPassword,
    @NotBlank(message = "New password is required")
    String newPassword
) {
    
}
