package org._iir.backend.modules.order.demande;

import org._iir.backend.modules.demande.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DemandeOrderRepository extends JpaRepository<DemandeOrder,Long> {

    
}
