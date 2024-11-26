package org._iir.backend.dao;

import org._iir.backend.bean.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffreDao extends JpaRepository<Offre, Integer> {
    List<Offre> findByPrestataireId(int prestataireId);
}
