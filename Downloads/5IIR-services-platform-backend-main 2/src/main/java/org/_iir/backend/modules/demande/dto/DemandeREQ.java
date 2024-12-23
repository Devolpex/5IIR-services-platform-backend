package org._iir.backend.modules.demande.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record DemandeREQ(
        @NotBlank(message = "Service name must not be blank.")
        @Size(max = 100, message = "Service name must not exceed 100 characters.")
        String service,

        @NotBlank(message = "Description must not be blank.")
        @Size(max = 500, message = "Description must not exceed 500 characters.")
        String description,

        @NotBlank(message = "Lieu must not be blank.")
        @Size(max = 255, message = "Lieu must not exceed 255 characters.")
        String lieu,

        @NotNull(message = "Date disponible must not be null.")
        @Future(message = "Date disponible must be a future date.")
        Date dateDisponible

) {
}
