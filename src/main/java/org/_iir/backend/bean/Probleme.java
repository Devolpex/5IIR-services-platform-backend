package org._iir.backend.bean;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Probleme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private Date dateSignalement;

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

    public Date getDateSignalement() {
        return dateSignalement;
    }

    public void setDateSignalement(Date dateSignalement) {
        this.dateSignalement = dateSignalement;
    }

    public User getUtilisateurSignale() {
        return utilisateurSignale;
    }

    public void setUtilisateurSignale(User utilisateurSignale) {
        this.utilisateurSignale = utilisateurSignale;
    }
}
