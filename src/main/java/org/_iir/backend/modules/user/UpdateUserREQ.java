package org._iir.backend.modules.user;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateUserREQ(
    
    @NotBlank(message = "Nom cannot be blank")
    String nom,

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    String email,

    @Nullable
    String password,

    @NotBlank(message = "Role cannot be blank")
    @Pattern(regexp = "ADMIN|PRESTATAIRE|DEMANDEUR", message = "Role should be ADMIN, PRESTATAIRE or DEMANDEUR")
    String role
) {
    
}
