package org._iir.backend.bean;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Prestataire extends User {
    @OneToMany(mappedBy = "prestataire")
    private List<Service> services;

    @OneToMany(mappedBy = "prestataire")
    private List<Proposition> propositions;

    @OneToMany(mappedBy = "prestataire")
    private List<Offre> offres;


    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Proposition> getPropositions() {
        return propositions;
    }

    public void setPropositions(List<Proposition> propositions) {
        this.propositions = propositions;
    }

    public List<Offre> getOffres() {
        return offres;
    }

    public void setOffres(List<Offre> offres) {
        this.offres = offres;
    }
}
