package com.example.ClientAzure.Service;

import com.example.ClientAzure.Bean.Client;

import java.util.List;

public interface ClientService {
    List<Client> getClients();
    Client getClient(long id);
    Client addClient(Client client);
    void deleteClient(long id);
    void updateClient(Client client);
}
