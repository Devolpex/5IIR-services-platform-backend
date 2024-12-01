package org._iir.backend.modules.order.demande;

import org._iir.backend.modules.order.dto.DemandeOrderDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
