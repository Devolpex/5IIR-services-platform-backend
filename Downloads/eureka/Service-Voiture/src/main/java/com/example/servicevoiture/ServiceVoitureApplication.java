package com.example.servicevoiture;

import com.example.servicevoiture.entities.Client;
import com.example.servicevoiture.entities.Voiture;
import com.example.servicevoiture.repositories.VoitureRepository;
import com.example.servicevoiture.service.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient

public class ServiceVoitureApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVoitureApplication.class, args);
    }
}

