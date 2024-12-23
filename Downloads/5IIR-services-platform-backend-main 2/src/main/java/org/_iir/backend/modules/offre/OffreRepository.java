package org._iir.backend.modules.offre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreRepository  extends JpaRepository<Offre, Long> {
    
}
