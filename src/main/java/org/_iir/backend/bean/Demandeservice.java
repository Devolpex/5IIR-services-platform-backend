package org._iir.backend.bean;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Demandeservice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String typeServiceRecherche;
    private String description;
    private String lieu;
    private Date datesDisponibles;

    @ManyToOne
    private Demandeur demandeur;

    @OneToMany(mappedBy = "demandeService")
    private List<Proposition> propositions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeServiceRecherche() {
        return typeServiceRecherche;
    }

    public void setTypeServiceRecherche(String typeServiceRecherche) {
        this.typeServiceRecherche = typeServiceRecherche;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDatesDisponibles() {
        return datesDisponibles;
    }

    public void setDatesDisponibles(Date datesDisponibles) {
        this.datesDisponibles = datesDisponibles;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public List<Proposition> getPropositions() {
        return propositions;
    }

    public void setPropositions(List<Proposition> propositions) {
        this.propositions = propositions;
    }
}
