package org._iir.backend.modules.user;

import java.util.List;

import org._iir.backend.exception.OwnAlreadyExistsException;
import org._iir.backend.interfaces.IService;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.prestataire.Prestataire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IService<User, UserDTO, CreateUserREQ, UpdateUserREQ, Long> {

    private final UserRepository userRepository;
    private final UserMapperImpl userMapper;
    private final PasswordEncoder passwordEncoder;

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * Creates a new user based on the provided user request.
     * The method checks if the email is already in use, and if not, it creates a
     * new user
     * based on the specified role (e.g., DEMANDEUR, PRESTATAIRE, or ADMIN).
     * The password is encoded before saving the user.
     * 
     * @param req the request object containing the user details (name, email,
     *            password, and role).
     * @return a UserDTO object representing the saved user.
     * @throws OwnAlreadyExistsException if the email is already in use by another user.
     */
    @Override
    public UserDTO create(CreateUserREQ req) {
        // Check if the email is already used
        if (userRepository.existsByEmail(req.email())) {
            throw new OwnAlreadyExistsException("The email " + req.email() + " already exists", "email");
        }

        // Build the user based on the role
        User user = User
                .builder()
                .nom(req.nom())
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .role(Role.valueOf(req.role())) 
                .accountVerified(true)
                .build();

        // Save the user based on the role
        if (user.getRole().equals(Role.DEMANDEUR)) {
            user = this.saveDemandeur(user);
        } else if (user.getRole().equals(Role.PRESTATAIRE)) {
            user = this.savePrestataire(user);
        } else {
            user = userRepository.save(user);
        }

        // Return the user DTO
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO update(UpdateUserREQ req, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public UserDTO findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<UserDTO> findList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findList'");
    }

    @Override
    public Page<UserDTO> findPage(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPage'");
    }

    private User saveDemandeur(User user) {
        // Save the demandeur
        Demandeur demandeur = Demandeur
                .builder()
                .nom(user.getNom())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
        return userRepository.save(demandeur);
    }

    private User savePrestataire(User user) {
        // Save the prestataire
        Prestataire prestataire = Prestataire
                .builder()
                .nom(user.getNom())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
        return userRepository.save(prestataire);
    }
}
