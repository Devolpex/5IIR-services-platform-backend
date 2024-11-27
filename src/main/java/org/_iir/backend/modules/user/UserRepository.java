package org._iir.backend.modules.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    /**
     * Query method to search user by keyword (name, email)
     * 
     * @param keyword
     * @param pageable
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.nom LIKE %:keyword% OR u.email LIKE %:keyword%")
    Page<User> search(String keyword, Pageable pageable);

    boolean existsByEmailAndIdNot(
            String email,
            Long id);

}
