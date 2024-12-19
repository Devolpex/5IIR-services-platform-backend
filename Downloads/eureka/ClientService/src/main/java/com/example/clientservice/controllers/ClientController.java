package com.example.clientservice.controllers;

import com.example.clientservice.entities.Client;
import com.example.clientservice.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/clients")
public class ClientController {

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        try {
            // Rechercher le client dans la base de données
            Client client = clientRepository.findById(id)
                    .orElseThrow(() -> new Exception("Client introuvable avec l'ID : " + id));
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Retourner une réponse 404 si le client n'existe pas
        }
    }

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}
