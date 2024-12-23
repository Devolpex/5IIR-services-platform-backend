package org._iir.backend.modules.auth;

import org._iir.backend.modules.user.Role;

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
    private String email;
    private Role role;
    private String token;
    private boolean isVerified;
}
