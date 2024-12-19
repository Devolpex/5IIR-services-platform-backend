package com.example.servicevoiture.repositories;

import com.example.servicevoiture.entities.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {

}
