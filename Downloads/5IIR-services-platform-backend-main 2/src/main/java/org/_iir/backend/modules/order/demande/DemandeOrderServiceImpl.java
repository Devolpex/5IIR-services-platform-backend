package org._iir.backend.modules.order.demande;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.modules.demande.Demande;
import org._iir.backend.modules.demande.DemandeRepository;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.order.IOrder;
import org._iir.backend.modules.order.OrderStatus;
import org._iir.backend.modules.order.dto.DemandeOrderDTO;
import org._iir.backend.modules.prestataire.Prestataire;
import org._iir.backend.modules.proposition.Proposition;
import org._iir.backend.modules.proposition.PropositionDao;
import org._iir.backend.modules.user.Role;
import org._iir.backend.modules.user.User;
import org._iir.backend.modules.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(DemandeOrderServiceImpl.class);
    private final DemandeRepository demandeRepository;

    private final DemandeOrderRepository orderRepository;
    private final DemandeOrderMapperImpl orderMapper;
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

    /**
     * For bussiness logic, we dont need implement this method.
     */
    @Override
    public DemandeOrderDTO update(DemandeOrderREQ req, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        orderRepository.findById(id)
                .ifPresentOrElse(orderRepository::delete, () -> {
                    log.error("Order with id {} not found", id);
                    throw new OwnNotFoundException("Order not exists");
                });
    }

    @Override
    public DemandeOrderDTO findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Order with id {} not found", id);
                    return new OwnNotFoundException("Order not exists");
                });
    }

    @Override
    public List<DemandeOrderDTO> findList() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public Page<DemandeOrderDTO> findPage(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toDTO);
    }

    /**
     * Bug: The method fetchOrdersByUser() in DemandeOrderServiceImpl.java is not
     * implemented correctly. The method is supposed to fetch orders by the current
     */
    @Override
    public List<DemandeOrderDTO> fetchOrdersByUser() {
        // Get the currently authenticated user
        User user = userService.getAuthenticatedUser();

        // Check if the user is a Demandeur
        if (user.getRole().equals(Role.DEMANDEUR)) {
            Demandeur demandeur = (Demandeur) user;

            // List to store the orders
            List<DemandeOrderDTO> orders = new ArrayList<>();

            // Loop through the list of demandes (the Demandeur's requests)
            for (Demande demande : demandeur.getDemandes()) {
                // For each demande, check if it has a related proposition and demandeOrder
                for (Proposition proposition : demande.getPropositions()) {
                    DemandeOrder demandeOrder = proposition.getDemandeOrder();

                    if (demandeOrder != null) {
                        // Convert the DemandeOrder entity to DemandeOrderDTO
                        DemandeOrderDTO orderDTO = orderMapper.toDTO(demandeOrder);
                        orders.add(orderDTO); // Add to the list of orders
                    }
                }
            }

            return orders;
        }

        // Return an empty list or null if the user is not a Demandeur
        return new ArrayList<>();
    }

    @Override
    public DemandeOrderDTO confirmOrder(Long orderId) {
        // Find the order
        DemandeOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order with id {} not found", orderId);
                    return new OwnNotFoundException("Order not exists");
                });
        // Check if the current prestataire is the owner of proposition
        Prestataire prestataire = (Prestataire) userService.getAuthenticatedUser();
        if (!order.getProposition().getPrestataire().getId().equals(prestataire.getId())) {
            log.error("Prestataire with id {} is not the owner of proposition with id {}",
                    prestataire.getId(),
                    order.getProposition().getId());
            throw new OwnNotFoundException("Prestataire is not the owner of proposition");
        }
        // Check if the order is already confirmed
        if (order.getStatus().equals(OrderStatus.CONFIRMED)) {
            log.error("Order with id {} is already confirmed", orderId);
            throw new OwnNotFoundException("Order is already confirmed");
        }
        // Confirm the order
        order.setStatus(OrderStatus.CONFIRMED);
        order = orderRepository.save(order);

        // Sent Email to the demandeur
        // Sent Email to the prestataire

        return orderMapper.toDTO(order);
    }

    @Override
    public DemandeOrderDTO cancelOrder(Long orderId) {
        // Find the order
        DemandeOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order with id {} not found", orderId);
                    return new OwnNotFoundException("Order not exists");
                });
        // Get the current user
        User user = userService.getAuthenticatedUser();
        // Traitment for demandeur
        if (user.getRole().equals(Role.DEMANDEUR)) {
            // Check if the current demandeur is the owner of demande
            Demandeur demandeur = (Demandeur) user;
            if (!order.getProposition().getDemande().getDemandeur().getId().equals(demandeur.getId())) {
                log.error("Demandeur with id {} is not the owner of demande with id {}",
                        demandeur.getId(),
                        order.getProposition().getDemande().getId());
                throw new OwnNotFoundException("Demandeur is not the owner of demande");
            }
            // Check if the order is already canceled
            if (order.getStatus().equals(OrderStatus.CANCELED)) {
                log.error("Order with id {} is already canceled", orderId);
                throw new OwnNotFoundException("Order is already canceled");
            }
            // Cancel the order
            order.setStatus(OrderStatus.CANCELED);
            order = orderRepository.save(order);
        }
        // Traitment for prestataire
        else if (user.getRole().equals(Role.PRESTATAIRE)) {
            // Check if the current prestataire is the owner of proposition
            Prestataire prestataire = (Prestataire) user;
            if (!order.getProposition().getPrestataire().getId().equals(prestataire.getId())) {
                log.error("Prestataire with id {} is not the owner of proposition with id {}",
                        prestataire.getId(),
                        order.getProposition().getId());
                throw new OwnNotFoundException("Prestataire is not the owner of proposition");
            }
            // Check if the order is already canceled
            if (order.getStatus().equals(OrderStatus.CANCELED)) {
                log.error("Order with id {} is already canceled", orderId);
                throw new OwnNotFoundException("Order is already canceled");
            }
            // Cancel the order
            order.setStatus(OrderStatus.CANCELED);
            order = orderRepository.save(order);
        }

        // Sent Email to the demandeur
        // Sent Email to the prestataire

        return orderMapper.toDTO(order);
    }


    public List<DemandeOrderDTO> getConfirmedDemandesByDemandeur() {
        User user = userService.getAuthenticatedUser();
        if (user instanceof Demandeur demandeur) {
            return orderRepository
                    .findByPropositionDemandeDemandeurEmailAndStatus(demandeur.getEmail(), OrderStatus.CONFIRMED)
                    .stream()
                    .map(orderMapper::toDTO)
                    .toList();
        }
        throw new OwnNotFoundException("Seul un demandeur peut consulter ses demandes approuvées.");
    }
}
