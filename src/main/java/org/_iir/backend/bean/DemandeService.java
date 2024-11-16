package org._iir.backend.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandeService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String service;
    private String description;
    private String lieu;
    private Date datesDisponibles;

    @ManyToOne
    @JoinColumn(name = "demandeur_id")
    private Demandeur demandeur;

    @OneToMany(mappedBy = "demandeService")
    private List<Proposition> propositions;

}
