package org._iir.backend.modules.proposition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PropositionDao extends JpaRepository<Proposition, Long> {

    @Query("SELECT p FROM Proposition p WHERE p.demande.id = :demandeId")
    List<Proposition> findByDemandeId(@Param("demandeId") Long demandeId);
    
}
