package org._iir.backend.modules.prestataire;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import org._iir.backend.modules.offre.Offre;
import org._iir.backend.modules.proposition.Proposition;
import org._iir.backend.modules.service.Service;
import org._iir.backend.modules.user.User;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
public class Prestataire extends User {
    @OneToMany(mappedBy = "prestataire", fetch = FetchType.EAGER)
    private List<Service> services;

    @OneToMany(mappedBy = "prestataire", fetch = FetchType.EAGER)
    private List<Proposition> propositions;

    @OneToMany(mappedBy = "prestataire", fetch = FetchType.EAGER)
    private List<Offre> offres;

}
