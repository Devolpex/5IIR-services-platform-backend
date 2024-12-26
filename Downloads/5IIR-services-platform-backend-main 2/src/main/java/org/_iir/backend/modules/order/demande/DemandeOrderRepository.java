package org._iir.backend.modules.order.demande;

import org._iir.backend.modules.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DemandeOrderRepository extends JpaRepository<DemandeOrder,Long> {

    List<DemandeOrder> findByPropositionDemandeDemandeurEmailAndStatus(String email, OrderStatus status);

}
