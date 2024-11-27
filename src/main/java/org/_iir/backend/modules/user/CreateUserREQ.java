package org._iir.backend.modules.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateUserREQ(
    
    @NotBlank(message = "Nom cannot be blank")
    String nom,

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    String email,

    @NotBlank(message = "Password cannot be blank")
    String password,

    @NotBlank(message = "Role cannot be blank")
    @Pattern(regexp = "ADMIN|PRESTATAIRE|DEMANDEUR", message = "Role should be ADMIN, PRESTATAIRE or DEMANDEUR")
    String role
) {
    
}
