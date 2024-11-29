package org._iir.backend.modules.auth;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org._iir.backend.exception.InvalidTokenException;
import org._iir.backend.exception.UserAleradyExistException;
import org._iir.backend.modules.auth.mail.AccountVerificationEmailContext;
import org._iir.backend.modules.auth.mail.EmailService;
import org._iir.backend.modules.auth.mail.SecureTokenService;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.user.SecureToken;
import org._iir.backend.modules.user.User;
import org._iir.backend.modules.user.UserRepository;
import org._iir.backend.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    private String baseUrl = "http://localhost:8081";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SecureTokenService secureTokenService;

    public void register(RegistrationRequest request) throws UserAleradyExistException {

        if (isUserExist(request.email())) {
            throw new UserAleradyExistException("User already exist");
        }
        User user = new User();
        user.setNom(request.nom());
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setAccountVerified(false); // Assuming account is not verified initially
        user.setPassword(passwordEncoder.encode(request.password())); // Hashing the password

        userRepository.save(user); // Save user to the database
        sendRegistrationEmail(user); // Send a registration confirmation email (if required)
    }

    public boolean isUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void sendRegistrationEmail(User user) {

        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenService.saveSecureToken(secureToken);

        // send email
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setVerifyToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseUrl, secureToken.getToken());
        try {
            emailService.sendEmail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyAccount(String token) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || secureToken.isExpired()) {
            throw new InvalidTokenException("Invalid or expired token");
        }
        User user = userRepository.getById((secureToken.getUser().getId()));
        if (Objects.isNull(user)) {
            throw new InvalidTokenException("User not found");

        }
        user.setAccountVerified(true);
        userRepository.save(user);
        secureTokenService.removeToken(token);
        return true;
    }

    public UserDTO login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        User user = (User) authentication.getPrincipal();
        String token = jwtProvider.generateToken(user);
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }

    // /**
    //  * Retrieves the authenticated user from the security context.
    //  * 
    //  * @return the authenticated user, or null if no user is authenticated.
    //  */
    // public User getAuthenticatedUser() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication != null && authentication.isAuthenticated()) {
    //         return (User) authentication.getPrincipal();
    //     }
    //     return null;
    // }

    // public Demandeur getAuthenticatedDemandeur() {
    //     User authenticatedUser = this.getAuthenticatedUser();
    //     if (authenticatedUser instanceof Demandeur) {
    //         return (Demandeur) authenticatedUser;
    //     } else {
    //         return null;
    //     }
    // }

}
