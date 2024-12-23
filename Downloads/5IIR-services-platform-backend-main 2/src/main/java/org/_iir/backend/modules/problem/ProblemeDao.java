package org._iir.backend.modules.problem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemeDao extends JpaRepository<Problem, Long> {
}
