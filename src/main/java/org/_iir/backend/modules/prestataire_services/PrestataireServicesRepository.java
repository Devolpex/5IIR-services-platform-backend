package org._iir.backend.modules.prestataire_services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestataireServicesRepository extends JpaRepository<PrestataireServices, PrestataireServiceID> {

}
