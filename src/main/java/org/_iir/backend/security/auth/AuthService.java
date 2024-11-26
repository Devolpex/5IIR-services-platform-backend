package org._iir.backend.security.auth;

import lombok.RequiredArgsConstructor;
import org._iir.backend.dao.UserDao;
import org._iir.backend.security.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org._iir.backend.bean.User;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public UserDto login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );
        User user = (User) authentication.getPrincipal();
        String token = jwtProvider.generateToken(user);
        return UserDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .role(user.getRole())
            .token(token)
            .build();
    }
}
