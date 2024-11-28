package org._iir.backend.modules.account;

import org._iir.backend.exception.OwnAlreadyExistsException;
import org._iir.backend.exception.PasswordIncorrectException;
import org._iir.backend.modules.user.User;
import org._iir.backend.modules.user.UserRepository;
import org._iir.backend.modules.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AccountDTO getInfos() {
        User user = userService.getAuthenticatedUser();
        return AccountDTO.builder()
                .id(user.getId())
                .nom(user.getNom())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    // Update infos
    public AccountDTO updateInfos(InfoREQ req) {
        User user = userService.getAuthenticatedUser();
        // Check if the email already exists
        if (!user.getEmail().equals(req.email()) && userRepository.existsByEmail(req.email())) {
            throw new OwnAlreadyExistsException("Email already exists","email");
        }
        user.setNom(req.nom());
        user.setEmail(req.email());
        user = userRepository.save(user);
        return AccountDTO.builder()
                .id(user.getId())
                .nom(user.getNom())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    // Update password
    public void updatePassword(PasswordREQ req){
        // Get the current user
        User user = userService.getAuthenticatedUser();
        // Check if the old password is correct 
        if (!passwordEncoder.matches(req.oldPassword(), user.getPassword())) {
            throw new PasswordIncorrectException("Old password is incorrect","oldPassword");
        } else {
            // Update the password
            user.setPassword(passwordEncoder.encode(req.newPassword()));
            userRepository.save(user);
        }
    }

}
