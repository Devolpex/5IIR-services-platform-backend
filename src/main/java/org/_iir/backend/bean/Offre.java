package org._iir.backend.bean;

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
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "demande_service_id")
    private DemandeService demandeService;

    public DemandeService getDemandeService() {
        return demandeService;
    }



    private String description;
    private Double tarif;
    private DisponibiliteStatus disponibilite;

    @ManyToOne
    private Prestataire prestataire;


    @ManyToOne
    private Demandeur demandeur;


}
