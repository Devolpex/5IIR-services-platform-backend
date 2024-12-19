package com.example.clientservice;

import com.example.clientservice.entities.Client;
import com.example.clientservice.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
@EnableDiscoveryClient
@SpringBootApplication
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

        @Bean
        public CommandLineRunner initDatabase(ClientRepository clientRepository) {
            return args -> {
                clientRepository.save(new Client(null, "Mouna", 20));
                clientRepository.save(new Client(null, "Imane", 24));
            };
        }

    }

