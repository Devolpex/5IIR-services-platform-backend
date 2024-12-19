package com.example.servicevoiture.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voiture {
    @Id
    @GeneratedValue
    private Long id;
    private String marque;
    private String matricule;
    private String model;
    @Column(name = "id_client")
    private Long idClient;
    @Transient
    private Client client; // Objet non persisté pour les réponses JSON
}

