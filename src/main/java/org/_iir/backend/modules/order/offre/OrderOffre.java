package org._iir.backend.modules.order.offre;

import java.util.Date;
import java.util.Objects;

import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.offre.Offre;
import org._iir.backend.modules.order.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "order_offre")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = { "demandeur", "offre" })
public class OrderOffre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "offre_id")
    private Offre offre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "demandeur_id")
    private Demandeur demandeur;


    // Eaquels
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderOffre that = (OrderOffre) o;
        return id == that.id;
    }

    // Hash Code
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
