package org._iir.backend.modules.prestataire_services;

import java.util.Objects;
import java.util.Set;

import org._iir.backend.modules.offre.Offre;
import org._iir.backend.modules.prestataire.Prestataire;
import org._iir.backend.modules.service.Service;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prestataire_service")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrestataireServices {

    @EmbeddedId
    private PrestataireServiceID id;
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("prestataireId") 
    @JoinColumn(name = "prestataire_id")
    private Prestataire prestataire;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("serviceId") 
    @JoinColumn(name = "service_id")
    private Service service;

    @OneToMany(mappedBy = "prestataireService", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Offre> offres;

    // To String
    @Override
    public String toString() {
        return "PrestataireServices [id=" + id + ", prestataire=" + prestataire + ", service=" + service + "]";
    }

    // Equals
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PrestataireServices that = (PrestataireServices) o;
        return id.equals(that.id);
    }

    // Hash Code
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
