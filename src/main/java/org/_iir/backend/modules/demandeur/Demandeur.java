package org._iir.backend.modules.demandeur;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import org._iir.backend.modules.demande.Demande;
import org._iir.backend.modules.order.offre.OrderOffre;
import org._iir.backend.modules.user.User;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class Demandeur extends User {
    @OneToMany(mappedBy = "demandeur", fetch = FetchType.EAGER)
    private List<Demande> demandes;

    @OneToMany(mappedBy = "demandeur", fetch = FetchType.EAGER)
    private List<OrderOffre> offreOrders;

}
