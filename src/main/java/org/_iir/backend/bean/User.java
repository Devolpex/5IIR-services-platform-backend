package org._iir.backend.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org._iir.backend.enums.Role;

import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private boolean accountVerified =false;

    private Role role;

    private boolean loginDisabled;


    @OneToMany(mappedBy = "user")
    Set <SecureToken> secureTokens;



}
