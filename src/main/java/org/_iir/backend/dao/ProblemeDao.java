package org._iir.backend.dao;

import org._iir.backend.bean.Probleme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemeDao extends JpaRepository<Probleme, Integer> {
}
