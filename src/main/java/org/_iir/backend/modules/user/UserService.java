package org._iir.backend.modules.user;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org._iir.backend.exception.OwnAlreadyExistsException;
import org._iir.backend.exception.OwnNotFoundException;
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

/**
 * Service class responsible for managing users.
 * Provides operations for creating, updating, deleting, and retrieving users.
 * Supports different user roles (e.g., DEMANDEUR, PRESTATAIRE, ADMIN).
 */
@Service
@RequiredArgsConstructor
public class UserService implements IService<User, UserDTO, CreateUserREQ, UpdateUserREQ, Long> {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private final UserRepository userRepository;
    private final UserMapperImpl userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves the authenticated user from the security context.
     * 
     * @return the authenticated user, or null if no user is authenticated.
     */
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * Creates a new user based on the provided user request.
     * Checks if the email is already in use, and if not, creates a new user 
     * with the specified role (e.g., DEMANDEUR, PRESTATAIRE, or ADMIN). 
     * The password is encoded before saving the user.
     * 
     * @param req the request object containing the user details (name, email, password, and role).
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
        user = this.saveUserByRole(user);

        // Return the user DTO
        return userMapper.toDTO(user);
    }

    /**
     * Updates an existing user based on the provided request and user ID.
     * If the user is found, updates their details and returns the updated user DTO.
     * If the user is not found, throws an OwnNotFoundException.
     * 
     * @param req the request object containing the updated user details (name, email, and optional password).
     * @param id the ID of the user to be updated.
     * @return the updated UserDTO object.
     * @throws OwnNotFoundException if the user with the specified ID is not found.
     */
    @Override
    public UserDTO update(UpdateUserREQ req, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    if (userRepository.existsByEmailAndIdNot(req.email(), id)) {
                        throw new OwnAlreadyExistsException("The email " + req.email() + " already exists", "email");
                    }
                    user.setNom(req.nom());
                    user.setEmail(req.email());
                    Optional.ofNullable(req.password())
                            .ifPresent(password -> user.setPassword(passwordEncoder.encode(password)));
                    return userMapper.toDTO(userRepository.save(user));
                })
                .orElseThrow(() -> {
                    logger.warning("User with id " + id + " not found");
                    return new OwnNotFoundException("User with id " + id + " not found");
                });
    }

    /**
     * Deletes a user based on the provided user ID.
     * If the user is found, deletes the user. If not, throws an OwnNotFoundException.
     * 
     * @param id the ID of the user to be deleted.
     * @throws OwnNotFoundException if the user with the specified ID is not found.
     */
    @Override
    public void delete(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete,
                        () -> this.handleNotFound("User with id " + id + " not found"));
    }

    /**
     * Retrieves a user by their ID.
     * If the user is found, returns their UserDTO representation.
     * If the user is not found, throws an OwnNotFoundException.
     * 
     * @param id the ID of the user to be retrieved.
     * @return the UserDTO representation of the found user.
     * @throws OwnNotFoundException if the user with the specified ID is not found.
     */
    @Override
    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> this.handleNotFound("User with id " + id + " not found"));
    }

    /**
     * Retrieves a list of all users.
     * 
     * @return a list of UserDTO objects representing all users.
     */
    @Override
    public List<UserDTO> findList() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    /**
     * Retrieves a page of users based on the provided pageable object.
     * 
     * @param pageable the pagination and sorting information.
     * @return a Page of UserDTO objects representing the users.
     */
    @Override
    public Page<UserDTO> findPage(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDTO);
    }

    /**
     * Searches for users based on a keyword (e.g., name, email) and returns a page of matching users.
     * 
     * @param keyword the search keyword (e.g., name, email).
     * @param pageable the pagination and sorting information.
     * @return a Page of UserDTO objects representing the matching users.
     */
    public Page<UserDTO> search(String keyword, Pageable pageable) {
        return userRepository.search(keyword, pageable)
                .map(userMapper::toDTO);
    }

    /**
     * Saves a user as a Demandeur.
     * 
     * @param user the user to be saved as Demandeur.
     * @return the saved User object.
     */
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

    /**
     * Saves a user as a Prestataire.
     * 
     * @param user the user to be saved as Prestataire.
     * @return the saved User object.
     */
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

    /**
     * Saves a user based on their role (e.g., DEMANDEUR, PRESTATAIRE, or ADMIN).
     * 
     * @param user the user to be saved.
     * @return the saved User object.
     */
    private User saveUserByRole(User user) {
        switch (user.getRole()) {
            case DEMANDEUR:
                return saveDemandeur(user);
            case PRESTATAIRE:
                return savePrestataire(user);
            default:
                return userRepository.save(user);
        }
    }

    /**
     * Logs an error and throws a not-found exception.
     * 
     * @param message the error message.
     * @return the exception to be thrown.
     */
    private OwnNotFoundException handleNotFound(String message) {
        logger.warning(message);
        throw new OwnNotFoundException(message);
    }
}
