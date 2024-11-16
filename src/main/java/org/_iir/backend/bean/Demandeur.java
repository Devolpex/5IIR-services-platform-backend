package org._iir.backend.bean;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Demandeur extends User {
    @OneToMany(mappedBy = "demandeur")
    private List<Demandeservice> demandes;

    @OneToMany(mappedBy = "demandeur")
    private List<Offre> offres;

    public List<Demandeservice> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demandeservice> demandes) {
        this.demandes = demandes;
    }

    public List<Offre> getOffres() {
        return offres;
    }

    public void setOffres(List<Offre> offres) {
        this.offres = offres;
    }
}
