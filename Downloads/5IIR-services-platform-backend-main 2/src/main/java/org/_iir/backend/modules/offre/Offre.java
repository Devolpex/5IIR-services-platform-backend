package org._iir.backend.modules.offre;

import java.util.List;

import org._iir.backend.modules.order.offre.OrderOffre;
import org._iir.backend.modules.prestataire_services.PrestataireServices;

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
public class Offre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double tarif;

    @OneToMany(mappedBy = "offre", fetch = FetchType.EAGER)
    private List<OrderOffre> orders;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "prestataire_id", referencedColumnName = "prestataire_id"),
            @JoinColumn(name = "service_id", referencedColumnName = "service_id")
    })
    private PrestataireServices prestataireService;

    // To String
    @Override
    public String toString() {
        return "Offre [description=" + description + ", id=" + id + ", orders=" + orders + ", prestataireService="
                + prestataireService + ", tarif=" + tarif + "]";
    }

    // Equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Offre other = (Offre) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (orders == null) {
            if (other.orders != null)
                return false;
        } else if (!orders.equals(other.orders))
            return false;
        if (prestataireService == null) {
            if (other.prestataireService != null)
                return false;
        } else if (!prestataireService.equals(other.prestataireService))
            return false;
        if (tarif == null) {
            if (other.tarif != null)
                return false;
        } else if (!tarif.equals(other.tarif))
            return false;
        return true;
    }

    // Hash Code
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((orders == null) ? 0 : orders.hashCode());
        result = prime * result + ((prestataireService == null) ? 0 : prestataireService.hashCode());
        result = prime * result + ((tarif == null) ? 0 : tarif.hashCode());
        return result;
    }

 
}
