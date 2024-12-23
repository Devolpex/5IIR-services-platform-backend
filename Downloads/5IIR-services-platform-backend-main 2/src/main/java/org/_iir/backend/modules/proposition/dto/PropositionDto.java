package org._iir.backend.modules.proposition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropositionDto {
    private Long id;
    private String description;
    private Double tarifProposer;
    private Date dateDisponible;
    private DemandeDto demande;
    private PrestataireDto prestataire;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
