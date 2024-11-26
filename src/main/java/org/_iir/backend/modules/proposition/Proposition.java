package org._iir.backend.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Proposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double tarifPropose;
    private String disponibiliteProposee;

    @ManyToOne
    private DemandeService demandeService;

    @ManyToOne
    private Prestataire prestataire;
}
