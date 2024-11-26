package org._iir.backend.modules.evaluation;

import org._iir.backend.bean.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationDao extends JpaRepository<Evaluation, Integer> {
}
