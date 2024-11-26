package org._iir.backend.modules.offre;

import org._iir.backend.modules.demande.Demande;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.prestataire.Prestataire;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Offre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "demande_id")
    private Demande demandes;

    private String description;
    private Double tarif;
    private DisponibiliteStatus disponibilite;

    @ManyToOne
    private Prestataire prestataire;

    @ManyToOne
    private Demandeur demandeur;
}
