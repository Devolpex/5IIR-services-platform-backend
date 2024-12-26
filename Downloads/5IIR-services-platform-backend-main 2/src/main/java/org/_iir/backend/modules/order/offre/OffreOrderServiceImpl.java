package org._iir.backend.modules.order.offre;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.offre.Offre;
import org._iir.backend.modules.offre.OffreRepository;
import org._iir.backend.modules.order.IOrder;
import org._iir.backend.modules.order.OrderStatus;
import org._iir.backend.modules.order.dto.OffreOrderDTO;
import org._iir.backend.modules.prestataire.Prestataire;
import org._iir.backend.modules.user.Role;
import org._iir.backend.modules.user.User;
import org._iir.backend.modules.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OffreOrderServiceImpl implements IOrder<OrderOffre, OffreOrderDTO, OffreOrderREQ, OffreOrderREQ, Long> {

    private final OffreOrderRepository orderRepository;
    private final OffreRepository offreRepository;
    private final UserService userService;
    private final OffreOrderMapper orderMapper;

    @Override
    public OffreOrderDTO create(OffreOrderREQ req) {
        // Find the offre by id
        Offre offre = offreRepository.findById(req.offreId())
                .orElseThrow(() -> {
                    log.error("Offre not found with id: {}", req.offreId());
                    return new OwnNotFoundException("Offre not found");
                });
        // Find the Current User (Demandeur)
        Demandeur demandeur = (Demandeur) userService.getAuthenticatedUser();
        // Create the Order
        OrderOffre order = OrderOffre.builder()
                .offre(offre)
                .demandeur(demandeur)
                .orderDate(new Date())
                .status(OrderStatus.NEW)
                .build();
        // Save the Order
        order = orderRepository.save(order);

        // Sent Email confirmation to the demandeur
        // Sent Email notification to the prestataire

        return orderMapper.toDTO(order);
    }

    @Override
    public OffreOrderDTO update(OffreOrderREQ req, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        orderRepository.findById(id)
                .ifPresentOrElse(orderRepository::delete, () -> {
                    log.error("Order not found with id: {}", id);
                    throw new OwnNotFoundException("Order not found");
                });
    }

    @Override
    @Transactional
    public OffreOrderDTO findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Order not found with id: {}", id);
                    return new OwnNotFoundException("Order not found");
                });
    }

    @Override
    public List<OffreOrderDTO> findList() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public Page<OffreOrderDTO> findPage(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toDTO);
    }

    @Override
    public List<OffreOrderDTO> fetchOrdersByUser() {
        // Get the authenticated user
        User user = userService.getAuthenticatedUser();
        if (user.getRole().equals(Role.DEMANDEUR)) {
            Demandeur demandeur = (Demandeur) user;
            log.info("Demandeur From Order Service: {}", demandeur);
            // return demandeur.getOffreOrders()
            //         .stream()
            //         .map(orderMapper::toDTO)
            //         .toList();
        } else if (user.getRole().equals(Role.PRESTATAIRE)) {
            Prestataire prestataire = (Prestataire) user;
            log.info("Prestataire From Order Service: {}", prestataire);
            // Set<Offre> offres = new HashSet<>();
            // prestataire.getPrestataireServices()
            //         .forEach(prestataireService -> {
            //             offres.addAll(prestataireService.getOffres());
            //         });
            // return offres.stream()
            //         .flatMap(offre -> offre.getOrders().stream())
            //         .map(orderMapper::toDTO)
            //         .toList();

        }
        return null;
    }


    @Override
    public OffreOrderDTO confirmOrder(Long orderId) {
        // Récupérer l'utilisateur authentifié
        User user = userService.getAuthenticatedUser();

        // Récupérer la commande par son ID
        OrderOffre order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found with id: {}", orderId);
                    return new OwnNotFoundException("Order not found");
                });

        // Vérification : L'utilisateur est-il un demandeur ?
        if (user instanceof Demandeur demandeur) {
            // Vérifiez que le demandeur est associé à cette commande
            if (!order.getDemandeur().getId().equals(demandeur.getId())) {
                throw new OwnNotFoundException("Vous n'êtes pas le propriétaire de cette commande");
            }

            // Confirmer la commande
            order.setStatus(OrderStatus.CONFIRMED);
            order = orderRepository.save(order);

            log.info("Commande confirmée par le demandeur avec ID : {}", demandeur.getId());
            return orderMapper.toDTO(order);
        } else {
            throw new OwnNotFoundException("Seul un demandeur peut confirmer une commande");
        }
    }


    @Override
    public OffreOrderDTO cancelOrder(Long orderId) {
        User user = userService.getAuthenticatedUser();
        Prestataire prestataire = (Prestataire) user;
        OrderOffre order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found with id: {}", orderId);
                    return new OwnNotFoundException("Order not found");
                });
        // Check if the prestataire is the owner of the offre
        if (order.getOffre().getPrestataireService().getPrestataire().getId().equals(prestataire.getId())) {

            order.setStatus(OrderStatus.CANCELED);
            order = orderRepository.save(order);

            // Sent Email confirmation to the demandeur
            // Sent Email notification to the prestataire
            return orderMapper.toDTO(order);
        } else {
            throw new OwnNotFoundException("You are not the owner of the offre");
        }
    }
    public List<OffreOrderDTO> findConfirmedOffersByPrestataire() {
        // Récupérer l'utilisateur authentifié
        User user = userService.getAuthenticatedUser();

        if (user instanceof Prestataire prestataire) {
            // Récupérer les offres confirmées pour ce prestataire
            return prestataire.getPrestataireServices()
                    .stream()
                    .flatMap(prestataireService -> prestataireService.getOffres().stream())
                    .filter(offre -> offre.getOrders().stream().anyMatch(order -> order.getStatus() == OrderStatus.CONFIRMED))
                    .flatMap(offre -> offre.getOrders().stream())
                    .filter(order -> order.getStatus() == OrderStatus.CONFIRMED)
                    .map(orderMapper::toDTO)
                    .toList();
        } else {
            throw new OwnNotFoundException("Seul un prestataire peut voir ses offres confirmées.");
        }
    }
    public List<OffreOrderDTO> getConfirmedOffersByPrestataire() {
        User user = userService.getAuthenticatedUser();

        if (!(user instanceof Prestataire prestataire)) {
            log.error("L'utilisateur authentifié n'est pas un prestataire : {}", user.getEmail());
            throw new OwnNotFoundException("Seul un prestataire peut voir ses offres confirmées.");
        }

        Long prestataireId = prestataire.getId();
        log.info("Récupération des offres confirmées pour le prestataire avec ID : {}", prestataireId);

        List<OrderOffre> confirmedOrders = orderRepository.findByOffrePrestataireServicePrestataireIdAndStatus(
                prestataireId,
                OrderStatus.CONFIRMED
        );

        if (confirmedOrders.isEmpty()) {
            log.info("Aucune offre confirmée trouvée pour le prestataire avec ID : {}", prestataireId);
            return List.of(); // Retourner une liste vide si aucune commande confirmée n'existe
        }

        return confirmedOrders.stream()
                .map(orderMapper::toDTO)
                .toList();
    }
}
