package org._iir.backend.modules.order.offre;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreOrderRepository extends JpaRepository<OrderOffre, Long> {

    public List<OrderOffre> findByDemandeurId(Long demandeurId);
    
}
