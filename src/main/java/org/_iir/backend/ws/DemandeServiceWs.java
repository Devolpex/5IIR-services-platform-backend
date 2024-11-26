package org._iir.backend.ws;

import org._iir.backend.dto.demande_service.DemandeServiceDTO;
import org._iir.backend.request.DemandeServiceRequest;
import org._iir.backend.service.DemandeServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class DemandeServiceWs {

    private final DemandeServiceService service;

    @PostMapping("/api/demande-service")
    public ResponseEntity<DemandeServiceDTO> create(@RequestBody @Valid DemandeServiceRequest request) {
        DemandeServiceDTO dto = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/api/demande-service/{id}")
    public ResponseEntity<DemandeServiceDTO> update(@PathVariable Long id,
            @RequestBody @Valid DemandeServiceRequest request) {
        DemandeServiceDTO dto = service.update(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/api/demande-service/{id}")
    public ResponseEntity<DemandeServiceDTO> findById(
            @PathVariable Long id) {
        DemandeServiceDTO dto = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/api/demande-service/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * This APi for the prestaaire to browse all the demande service in the platform
     * 
     * @return
     */
    @GetMapping("/api/demande-service")
    public ResponseEntity<Page<DemandeServiceDTO>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String orderBy,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(orderBy), sortBy));

        Page<DemandeServiceDTO> result = service.findPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

}
