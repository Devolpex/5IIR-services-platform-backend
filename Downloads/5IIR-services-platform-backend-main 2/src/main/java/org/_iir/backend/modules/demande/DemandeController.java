package org._iir.backend.modules.demande;

import java.util.List;

import org._iir.backend.modules.demande.dto.DemandeDTO;
import org._iir.backend.modules.demande.dto.DemandeREQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class DemandeController {

    private final DemandeService service;

    @PostMapping("/api/demande")
    public ResponseEntity<DemandeDTO> create(@RequestBody @Valid DemandeREQ request) {
        DemandeDTO dto = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/api/demande/{id}")
    public ResponseEntity<DemandeDTO> update(@PathVariable Long id,
                                             @RequestBody @Valid DemandeREQ request) {
        DemandeDTO dto = service.update(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @GetMapping("/api/demande/mes-demandes")
    public ResponseEntity<List<DemandeDTO>> getMyDemandes() {
        List<DemandeDTO> demandes = service.findDemandesByAuthenticatedDemandeur();
        return ResponseEntity.status(HttpStatus.OK).body(demandes);
    }


    @GetMapping("/api/demande/{id}")
    public ResponseEntity<DemandeDTO> findById(
            @PathVariable Long id) {
        DemandeDTO dto = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/api/demande/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/api/demande")
    public ResponseEntity<Page<DemandeDTO>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String orderBy,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(orderBy), sortBy));

        Page<DemandeDTO> result = service.findPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @GetMapping("/api/demande/list")
    public ResponseEntity<List<DemandeDTO>> findList() {
        List<DemandeDTO> result = service.findList();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
