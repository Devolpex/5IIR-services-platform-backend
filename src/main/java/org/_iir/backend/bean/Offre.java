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
<<<<<<< HEAD
    private Integer id;
=======
    private int id;
    @ManyToOne
    @JoinColumn(name = "demande_service_id")
    private Demandeservice demandeService;

    public Demandeservice getDemandeService() {
        return demandeService;
    }

    public void setDemandeService(Demandeservice demandeService) {
        this.demandeService = demandeService;
    }
>>>>>>> JAIM-27-API-pour-g-rer-la-publication-des-offres

    private String description;
    private Double tarif;
    private DisponibiliteStatus disponibilite;

    @ManyToOne
    private Prestataire prestataire;
<<<<<<< HEAD
=======

    @ManyToOne
    private Demandeur demandeur;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }
>>>>>>> JAIM-27-API-pour-g-rer-la-publication-des-offres
}
