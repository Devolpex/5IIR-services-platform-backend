package org._iir.backend.modules.proposition;

import org._iir.backend.modules.proposition.dto.PropositionDto;
import org._iir.backend.modules.proposition.dto.PropositionSaveReq;
import org._iir.backend.modules.proposition.dto.PropositionUpdateReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class PropositionController {
    
        private final PropositionService service;
    
        @PostMapping("/api/proposition")
        public ResponseEntity<PropositionDto> create(@RequestBody @Valid PropositionSaveReq request) {
            PropositionDto dto = service.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }

        @PreAuthorize("hasAuthority('PRESTATAIRE')")
        @PutMapping("/api/proposition/{id}")
        public ResponseEntity<PropositionDto> update(@PathVariable Long id,
                @RequestBody @Valid PropositionUpdateReq request) {
            PropositionDto dto = service.update(request, id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }

        @DeleteMapping("/api/proposition/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        @GetMapping("/api/proposition/{id}")
        public ResponseEntity<PropositionDto> findById(
                @PathVariable Long id) {
            PropositionDto dto = service.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        
}
