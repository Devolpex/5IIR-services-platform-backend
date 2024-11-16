package org._iir.backend.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class Demandeur extends User {
    @OneToMany(mappedBy = "demandeur")
    private List<DemandeService> demandes;

    @OneToMany(mappedBy = "demandeur")
    private List<Offre> offres;

}
