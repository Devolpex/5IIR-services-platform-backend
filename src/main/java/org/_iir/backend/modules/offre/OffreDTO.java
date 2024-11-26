package org._iir.backend.dto;

public class OffreDTO {
    private int id;
    private String description;
    private float tarif;
    private String disponibilite;
    private int prestataireId;

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

    public int getPrestataireId() {
        return prestataireId;
    }

    public void setPrestataireId(int prestataireId) {
        this.prestataireId = prestataireId;
    }
}
