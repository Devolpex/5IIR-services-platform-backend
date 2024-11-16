package org._iir.backend.dao;

import org._iir.backend.bean.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceDao extends JpaRepository<Service, Integer> {
}
