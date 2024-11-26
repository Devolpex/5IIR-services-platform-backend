package org._iir.backend.modules.evaluation;

import org._iir.backend.bean.Demandeur;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer note;
    private String commentaire;

    @ManyToOne
    private Prestataire prestataire;

    @ManyToOne
    private Demandeur demandeur;
  
}
