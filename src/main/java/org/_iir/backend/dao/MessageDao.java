package org._iir.backend.dao;

import org._iir.backend.bean.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDao extends JpaRepository<Message, Integer> {
}
