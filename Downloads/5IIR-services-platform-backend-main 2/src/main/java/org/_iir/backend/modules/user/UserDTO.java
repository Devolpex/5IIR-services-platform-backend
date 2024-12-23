package org._iir.backend.modules.user;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String nom;
    private String email;
    private Role role;
    private boolean accountVerified;

    // To String
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", accountVerified=" + accountVerified +
                '}';
    }

    // Equals
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDTO userDTO = (UserDTO) o;
        return id.equals(userDTO.id)
                && email.equals(userDTO.email)
                && role == userDTO.role
                && accountVerified == userDTO.accountVerified;
    }

    // Hash Code
    @Override
    public int hashCode() {
        return Objects.hash(id, email, role, accountVerified);
    }
}
