package org._iir.backend.bean;

import jakarta.persistence.*;

@Entity
public class Probleme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private String dateSignalement;

    @ManyToOne
    private User utilisateurSignale;

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

    public String getDateSignalement() {
        return dateSignalement;
    }

    public void setDateSignalement(String dateSignalement) {
        this.dateSignalement = dateSignalement;
    }

    public User getUtilisateurSignale() {
        return utilisateurSignale;
    }

    public void setUtilisateurSignale(User utilisateurSignale) {
        this.utilisateurSignale = utilisateurSignale;
    }
}
