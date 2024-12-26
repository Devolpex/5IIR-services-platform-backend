package org._iir.backend.modules.offre;

import java.util.List;

import org._iir.backend.modules.offre.dto.OffreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/offres")
public class OffreController {

    private final OffreServiceImpl service;

    @PostMapping
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<OffreDTO> create(@RequestBody OffreREQ req) {
        OffreDTO offre = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(offre);
    }

    // Endpoint to update an existing offre
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<OffreDTO> update(@RequestBody OffreREQ req, @PathVariable Long id) {
        OffreDTO offre = service.update(req, id);
        return ResponseEntity.status(HttpStatus.OK).body(offre);
    }

    // Endpoint to delete an existing offre
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Endpoint to get page of offres
    @GetMapping
    public ResponseEntity<Page<OffreDTO>> getPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "desc") String orderBy,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(orderBy), sortBy));
        Page<OffreDTO> offres = service.findPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(offres);
    }

    // Endpoint to get list of offres
    @GetMapping("/list")
    public ResponseEntity<List<OffreDTO>> getList() {
        List<OffreDTO> offres = service.findList();
        return ResponseEntity.status(HttpStatus.OK).body(offres);
    }

    // Endpoint to get an offre by id
    @GetMapping("/{id}")
    public ResponseEntity<OffreDTO> getById(@PathVariable Long id) {
        OffreDTO offre = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(offre);
    }
    @GetMapping("/my-offers")
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<List<OffreDTO>> getMyOffers() {
        List<OffreDTO> offres = service.findOffersByAuthenticatedPrestataire();
        return ResponseEntity.status(HttpStatus.OK).body(offres);
    }

}
