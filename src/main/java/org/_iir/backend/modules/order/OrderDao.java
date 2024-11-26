package org._iir.backend.dao;

import org._iir.backend.bean.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Integer> {
}
