package org._iir.backend.modules.offre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OffreREQ(
        @NotBlank(message = "Description is required")
        String description,
        @NotNull(message = "Tarif is required")
        Double tarif,
        @NotNull(message = "Prestataire ID is required")
        Long serviceId) {

}
