package org._iir.backend.modules.proposition;

import org._iir.backend.modules.demande.Demande;
import org._iir.backend.modules.prestataire.Prestataire;

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
    private Demande demande;

    @ManyToOne
    private Prestataire prestataire;
}
