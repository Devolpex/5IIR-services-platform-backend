package org._iir.backend.dao;

import org._iir.backend.bean.SecureToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecureTokenDao extends JpaRepository<SecureToken, Long> {

    SecureToken findByToken(final String token);
    Long deleteByToken(final String token);
}
