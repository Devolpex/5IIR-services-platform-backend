package org._iir.backend.modules.order.offre;

import java.util.List;

import org._iir.backend.modules.order.dto.DemandeOrderDTO;
import org._iir.backend.modules.order.dto.OffreOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order/offer")
@RequiredArgsConstructor
public class OffreOrderController {

    private final OffreOrderServiceImpl service;

    @PostMapping
    @PreAuthorize("hasAuthority('DEMANDEUR')")
    public ResponseEntity<OffreOrderDTO> createOrder(@RequestBody OffreOrderREQ req) {
        OffreOrderDTO order = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<OffreOrderDTO> getOrder(@PathVariable Long id) {
        OffreOrderDTO order = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<OffreOrderDTO>> getOrders() {
        List<OffreOrderDTO> orders = service.findList();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    // Endpoint to fetch by page
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<OffreOrderDTO>> getOrdersPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(order), sortBy));

        Page<OffreOrderDTO> orders = service.findPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    // Fetch order by user
    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('DEMANDEUR', 'PRESTATAIRE')")
    public ResponseEntity<List<OffreOrderDTO>> getOrdersByUser() {
        List<OffreOrderDTO> orders = service.fetchOrdersByUser();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @PatchMapping("/confirm/{id}")
    @PreAuthorize("hasAuthority('DEMANDEUR')")
    public ResponseEntity<OffreOrderDTO> confirmOrder(@PathVariable Long id) {
        OffreOrderDTO order = service.confirmOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @PatchMapping("/cancel/{id}")
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<OffreOrderDTO> cancelOrder(@PathVariable Long id) {
        OffreOrderDTO order = service.cancelOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/confirmed/my-offers")
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<List<OffreOrderDTO>> getConfirmedOffersByPrestataire() {
        List<OffreOrderDTO> confirmedOffers = service.getConfirmedOffersByPrestataire();
        return ResponseEntity.ok(confirmedOffers);
    }


}
