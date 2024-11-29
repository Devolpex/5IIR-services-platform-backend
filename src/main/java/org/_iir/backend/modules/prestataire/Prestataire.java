package org._iir.backend.modules.prestataire;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

import org._iir.backend.modules.prestataire_services.PrestataireServices;
import org._iir.backend.modules.proposition.Proposition;
import org._iir.backend.modules.user.User;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
public class Prestataire extends User {

    @OneToMany(mappedBy = "prestataire", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<PrestataireServices> prestataireServices;

    @OneToMany(mappedBy = "prestataire", fetch = FetchType.EAGER)
    private List<Proposition> propositions;

    @Override
    public String toString() {
        return "Prestataire [id=" + id + ", email=" + email + "]";
    }




}
