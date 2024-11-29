package org._iir.backend.modules.prestataire_services;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrestataireServiceID implements Serializable {
    private Long prestataireId;
    private Long serviceId;

    // Override equals and hashCode for composite key
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PrestataireServiceID that = (PrestataireServiceID) o;
        return Objects.equals(prestataireId, that.prestataireId) &&
                Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prestataireId, serviceId);
    }
}
