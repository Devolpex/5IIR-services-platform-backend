package org._iir.backend.dao;

import org._iir.backend.bean.Proposition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropositionDao extends JpaRepository<Proposition, Integer> {
}
