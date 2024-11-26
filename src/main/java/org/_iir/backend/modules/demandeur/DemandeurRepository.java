package org._iir.backend.modules.demandeur;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeurRepository extends JpaRepository<Demandeur, Long> {

    Optional<Demandeur> findByEmail(String email);

}
