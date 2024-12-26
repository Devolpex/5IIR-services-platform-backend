package org._iir.backend.modules.offre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffreRepository  extends JpaRepository<Offre, Long> {
    @Query("SELECT o FROM Offre o WHERE o.prestataireService.prestataire.id = :prestataireId")
    List<Offre> findByPrestataireId(@Param("prestataireId") Long prestataireId);
}
