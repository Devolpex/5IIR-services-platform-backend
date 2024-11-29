package org._iir.backend.modules.proposition.dto;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PropositionReq(
    @NotBlank(message = "Description must not be blank.")
    @Size(max = 500, message = "Description must not exceed 500 characters.")
    String description,
    @NotNull(message = "Price must not be null.")
    @Positive(message = "Price must be positive.")
    double tarifProposer,
    @NotNull(message = "Date de disponibilité must not be null.")
    @Future(message = "Date de disponibilité must be a future date.")
    Date dateDisponibilite,
    Long demandeId
) {

}