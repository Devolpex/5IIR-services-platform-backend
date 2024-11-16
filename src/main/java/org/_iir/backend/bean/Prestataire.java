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
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
public class Prestataire extends User {
    @OneToMany(mappedBy = "prestataire")
    private List<Service> services;

    @OneToMany(mappedBy = "prestataire")
    private List<Proposition> propositions;

    @OneToMany(mappedBy = "prestataire")
    private List<Offre> offres;

}
