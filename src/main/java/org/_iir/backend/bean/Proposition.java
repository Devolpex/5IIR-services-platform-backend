package org._iir.backend.bean;

import jakarta.persistence.*;

@Entity
public class Proposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float tarifPropose;
    private String disponibiliteProposee;

    @ManyToOne
    private Demandeservice demandeService;

    @ManyToOne
    private Prestataire prestataire;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTarifPropose() {
        return tarifPropose;
    }

    public void setTarifPropose(float tarifPropose) {
        this.tarifPropose = tarifPropose;
    }

    public String getDisponibiliteProposee() {
        return disponibiliteProposee;
    }

    public void setDisponibiliteProposee(String disponibiliteProposee) {
        this.disponibiliteProposee = disponibiliteProposee;
    }

    public Demandeservice getDemandeService() {
        return demandeService;
    }

    public void setDemandeService(Demandeservice demandeService) {
        this.demandeService = demandeService;
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }
}
