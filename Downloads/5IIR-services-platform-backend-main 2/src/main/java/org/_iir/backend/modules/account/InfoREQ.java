package org._iir.backend.modules.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InfoREQ(
    @NotBlank(message = "Nom is required")
    String nom,

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    String email
) {
    
}
