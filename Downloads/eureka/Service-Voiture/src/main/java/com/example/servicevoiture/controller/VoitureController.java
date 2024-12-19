package com.example.servicevoiture.controller;

import com.example.servicevoiture.entities.Voiture;
import com.example.servicevoiture.repositories.VoitureRepository;
import com.example.servicevoiture.service.ClientService;
import com.example.servicevoiture.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voitures")
public class VoitureController {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private ClientService clientService;
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            Voiture voiture = voitureRepository.findById(id)
                    .orElseThrow(() -> new Exception("Voiture Introuvable"));

            // Log pour debug
            System.out.println("Fetching client with id: " + voiture.getIdClient());

            Client client = clientService.getClientById(voiture.getIdClient());

            System.out.println("Client fetched: " + client);

            voiture.setClient(client);
            return ResponseEntity.ok(voiture);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération du client pour la voiture ID: " + id);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erreur : " + e.getMessage());
        }
    }



    @GetMapping("/test-client/{id}")
    public ResponseEntity<Client> testClient(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }
    @GetMapping
    public ResponseEntity<Object> findAll() {
        try {
            List<Voiture> voitures = voitureRepository.findAll();

            // Injecter les informations des clients
            for (Voiture voiture : voitures) {
                try {
                    Client client = clientService.getClientById(voiture.getIdClient());
                    voiture.setClient(client);
                } catch (Exception e) {
                    System.out.println("Erreur lors de la récupération du client pour la voiture ID: " + voiture.getId());
                    voiture.setClient(null); // Laisser le client null si une erreur se produit
                }
            }

            return ResponseEntity.ok(voitures);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching voitures: " + e.getMessage());
        }
    }


    // Récupérer une voiture par ID


    // Récupérer toutes les voitures pour un client donné
    @GetMapping("/client/{id}")
    public ResponseEntity<List<Voiture>> findByClient(@PathVariable Long id) {
        try {
            Client client = clientService.getClientById(id);
            if (client != null) {
                List<Voiture> voitures = voitureRepository.findAll(); // Ajoutez ici un filtre par client si nécessaire
                return ResponseEntity.ok(voitures);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Ajouter une nouvelle voiture


    // Mettre à jour une voiture existante
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Voiture updatedVoiture) {
        try {
            Voiture existingVoiture = voitureRepository.findById(id)
                    .orElseThrow(() -> new Exception("Voiture not found with ID: " + id));

            // Mise à jour des champs non-nuls
            if (updatedVoiture.getMatricule() != null && !updatedVoiture.getMatricule().isEmpty()) {
                existingVoiture.setMatricule(updatedVoiture.getMatricule());
            }
            if (updatedVoiture.getMarque() != null && !updatedVoiture.getMarque().isEmpty()) {
                existingVoiture.setMarque(updatedVoiture.getMarque());
            }
            if (updatedVoiture.getModel() != null && !updatedVoiture.getModel().isEmpty()) {
                existingVoiture.setModel(updatedVoiture.getModel());
            }

            Voiture savedVoiture = voitureRepository.save(existingVoiture);
            return ResponseEntity.ok(savedVoiture);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating voiture: " + e.getMessage());
        }
    }
    @PostMapping("/voitures")
    public ResponseEntity<Voiture> createVoiture(@RequestBody Voiture voiture) {
        try {
            Voiture savedVoiture = voitureRepository.save(voiture);
            return ResponseEntity.ok(savedVoiture);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
