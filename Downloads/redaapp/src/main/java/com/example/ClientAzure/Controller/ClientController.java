package com.example.ClientAzure.Controller;

import com.example.ClientAzure.Bean.Client;
import com.example.ClientAzure.Service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable long id) {
        return clientService.getClient(id);
    }

    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable long id) {
        clientService.deleteClient(id);
    }

    @PutMapping("/{id}")
    public void updateClient(@PathVariable long id, @RequestBody Client client) {
        client.setId(id);
        clientService.updateClient(client);
    }
}
