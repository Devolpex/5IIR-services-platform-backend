package org._iir.backend.modules.proposition.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandeDto {
    private Long id;
    private String service;
    private String description;
    private Date dateDisponible;
    private String lieu;
    private DemandeurDto demandeur;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
