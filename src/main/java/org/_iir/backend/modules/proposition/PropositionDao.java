package org._iir.backend.modules.proposition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropositionDao extends JpaRepository<Proposition, Long> {
}