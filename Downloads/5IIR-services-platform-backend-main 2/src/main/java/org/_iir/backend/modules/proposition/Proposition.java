package org._iir.backend.modules.proposition;

import java.time.LocalDateTime;
import java.util.Date;

import org._iir.backend.modules.demande.Demande;
import org._iir.backend.modules.order.demande.DemandeOrder;
import org._iir.backend.modules.prestataire.Prestataire;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Proposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double tarifProposer;
    @Temporal(TemporalType.DATE)
    private Date disponibiliteProposer;

    @ManyToOne()
    @JoinColumn(name = "demande_id")
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "prestataire_id")
    private Prestataire prestataire;

    @OneToOne(mappedBy = "proposition")
    private DemandeOrder demandeOrder;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
