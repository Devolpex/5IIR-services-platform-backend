package com.example.ClientAzure.Repository;

import com.example.ClientAzure.Bean.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
