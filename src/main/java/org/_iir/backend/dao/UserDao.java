package org._iir.backend.dao;

import org._iir.backend.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
