package org._iir.backend.modules.service;

import org._iir.backend.modules.prestataire.Prestataire;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prestataires")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeService;
    private String description;
    private Double tarif;
    private String disponibilite;

    @ManyToOne
    private Prestataire prestataire;

}
