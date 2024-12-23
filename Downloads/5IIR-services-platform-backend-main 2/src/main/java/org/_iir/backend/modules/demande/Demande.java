package org._iir.backend.modules.demande;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.proposition.Proposition;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"demandeur", "propositions"})
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String service;
    private String description;
    private String lieu;
    @Temporal(TemporalType.DATE)
    private Date dateDisponible;

    @ManyToOne
    @JoinColumn(name = "demandeur_id")
    private Demandeur demandeur;

    @OneToMany(mappedBy = "demande",fetch = FetchType.EAGER)
    private List<Proposition> propositions;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String emailDemandeur; // Nouveau champ pour stocker l'email du demandeur

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
