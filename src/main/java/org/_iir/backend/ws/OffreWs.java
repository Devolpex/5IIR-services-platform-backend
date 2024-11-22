package org._iir.backend.ws;

import org._iir.backend.dto.OffreDTO;
import org._iir.backend.service.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offres")
public class OffreWs {

    @Autowired
    private OffreService offreService;

    @PostMapping("/")
    public OffreDTO ajouterOffre(@RequestBody OffreDTO offreDTO) {
        return offreService.ajouterOffre(offreDTO);
    }

    @PutMapping("/{id}")
    public OffreDTO updateOffre(@PathVariable int id, @RequestBody OffreDTO offreDTO) {
        return offreService.updateOffre(id, offreDTO);
    }

    @GetMapping("/")
    public List<OffreDTO> findAllOffres() {
        return offreService.findAllOffres();
    }

    @DeleteMapping("/{id}")
    public void deleteOffre(@PathVariable int id) {
        offreService.deleteOffre(id);
    }
}
