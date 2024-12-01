package org._iir.backend.modules.order.demande;

import java.util.Date;
import java.util.List;

import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.modules.demande.Demande;
import org._iir.backend.modules.demande.DemandeRepository;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.order.IOrder;
import org._iir.backend.modules.order.OrderStatus;
import org._iir.backend.modules.order.dto.DemandeOrderDTO;
import org._iir.backend.modules.proposition.Proposition;
import org._iir.backend.modules.proposition.PropositionDao;
import org._iir.backend.modules.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemandeOrderServiceImpl
        implements IOrder<DemandeOrder, DemandeOrderDTO, DemandeOrderREQ, DemandeOrderREQ, Long> {

    private final DemandeOrderRepository orderRepository;
    private final DemandeOrderMapperImpl orderMapper;
    private final DemandeRepository demandeRepository;
    private final PropositionDao propositionDao;
    private final UserService userService;

    @Override
    public DemandeOrderDTO create(DemandeOrderREQ req) {
        // Find the proposition by id
        Proposition proposition = propositionDao.findById(req.propositionId())
                .orElseThrow(() -> {
                    log.error("Proposition with id {} not found", req.propositionId());
                    return new OwnNotFoundException("Proposition not exists");
                });

        // Check if the current demandeur is the owner of demande
        Demandeur demandeur = (Demandeur) userService.getAuthenticatedUser();
        if (!proposition.getDemande().getDemandeur().getId().equals(demandeur.getId())) {
            log.error("Demandeur with id {} is not the owner of demande with id {}",
                    demandeur.getId(),
                    proposition.getDemande().getId());
            throw new OwnNotFoundException("Demandeur is not the owner of demande");
        }

        // Create the order
        DemandeOrder order = DemandeOrder.builder()
                .proposition(proposition)
                .status(OrderStatus.NEW)
                .orderDate(new Date())
                .build();
        order = orderRepository.save(order);

        // Sent Email to the prestataire
        // Sent Email to the demandeur

        return orderMapper.toDTO(order);
    }

    @Override
    public DemandeOrderDTO update(DemandeOrderREQ req, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public DemandeOrderDTO findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<DemandeOrderDTO> findList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findList'");
    }

    @Override
    public Page<DemandeOrderDTO> findPage(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPage'");
    }

    @Override
    public List<DemandeOrderDTO> fetchOrdersByUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchOrdersByUser'");
    }

    @Override
    public DemandeOrderDTO confirmOrder(Long orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'confirmOrder'");
    }

    @Override
    public DemandeOrderDTO cancelOrder(Long orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelOrder'");
    }

}
