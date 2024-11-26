package org._iir.backend.modules.prestataire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PrestataireDao extends JpaRepository<Prestataire, Long> {
}
