package org._iir.backend.modules.order.demande;

import java.util.List;

import org._iir.backend.modules.order.dto.DemandeOrderDTO;
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
@RequiredArgsConstructor
@RequestMapping("/api/order/demande")
public class DemandeOrderController {

    private final DemandeOrderServiceImpl service;

    @PostMapping
    @PreAuthorize("hasAuthority('DEMANDEUR')")
    public ResponseEntity<DemandeOrderDTO> create(@RequestBody DemandeOrderREQ req) {
        DemandeOrderDTO order = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // Endpoint to fetch order by id
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DemandeOrderDTO> findById(@PathVariable Long id) {
        DemandeOrderDTO order = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    // Endpoint to fetch list of orders
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<DemandeOrderDTO>> findAll() {
        List<DemandeOrderDTO> orders = service.findList();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    // Endpoint to fetch list of orders by page
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<DemandeOrderDTO>> findAllPaged(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String orderBy) {

        // Create Pageable object
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.fromString(orderBy.toUpperCase()), sortBy);

        // Fetch paged result
        Page<DemandeOrderDTO> orders = service.findPage(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    // Endpoint to delete a order
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Endpoint to confirm a order
    @PatchMapping("/{id}/confirm")
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<DemandeOrderDTO> confirm(@PathVariable Long id) {
        DemandeOrderDTO order = service.confirmOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    // Endpoint to cancel a order
    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyAuthority('PRESTATAIRE', 'DEMANDEUR')")
    public ResponseEntity<DemandeOrderDTO> cancel(@PathVariable Long id) {
        DemandeOrderDTO order = service.cancelOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
    @GetMapping("/confirmed/my-demandes")
    @PreAuthorize("hasAuthority('DEMANDEUR')")
    public ResponseEntity<List<DemandeOrderDTO>> getConfirmedDemandesByDemandeur() {
        List<DemandeOrderDTO> confirmedDemandes = service.getConfirmedDemandesByDemandeur();
        return ResponseEntity.status(HttpStatus.OK).body(confirmedDemandes);
    }

}
