package org._iir.backend.modules.demande;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {


    @Query("SELECT d FROM Demande d WHERE d.demandeur.id = :demandeurId")
    List<Demande> findByDemandeurId(@Param("demandeurId") Long demandeurId);
}

