package org._iir.backend.user;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org._iir.backend.modules.user.*;
import org._iir.backend.exception.OwnAlreadyExistsException;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.prestataire.Prestataire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapperImpl userMapper;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void testCreateUserWithDemandeurRole() {
        // Arrange
        CreateUserREQ req = new CreateUserREQ("Marouane Dbibih", "marouane.dbibih@gmail.com", "password123", "DEMANDEUR");

        when(userRepository.existsByEmail(req.email())).thenReturn(false); // Email not taken
        when(userRepository.save(any(User.class))).thenReturn(new Demandeur()); // Mock the save operation
        when(userMapper.toDTO(any(User.class))).thenReturn(UserDTO.builder()
                .id(1L)
                .nom("Marouane Dbibih")
                .email("marouane.dbibih@gmail.com")
                .role(Role.DEMANDEUR)
                .build()); // Mock the userDTO mapping

        // Act
        UserDTO result = userService.create(req);

        // Assert
        assertNotNull(result);
        assertEquals("Marouane Dbibih", result.getNom());
        assertEquals("marouane.dbibih@gmail.com", result.getEmail());
        assertEquals(Role.DEMANDEUR, result.getRole());
        verify(userRepository, times(1)).existsByEmail(req.email());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toDTO(any(User.class));
    }

    @Test
    public void testCreateUserWithPrestataireRole() {
        // Arrange
        CreateUserREQ req = new CreateUserREQ("Marouane Dbibih", "marouane.dbibih@gmail.com", "password123", "PRESTATAIRE");

        when(userRepository.existsByEmail(req.email())).thenReturn(false); // Email not taken
        when(userRepository.save(any(User.class))).thenReturn(new Prestataire()); // Mock the save operation
        when(userMapper.toDTO(any(User.class)))
                .thenReturn(UserDTO.builder()
                .id(1L)
                .nom("Marouane Dbibih")
                .email("marouane.dbibih@gmail.com")
                .role(Role.PRESTATAIRE)
                .build()); 

        // Act
        UserDTO result = userService.create(req);

        // Assert
        assertNotNull(result);
        assertEquals("Marouane Dbibih", result.getNom());
        assertEquals("marouane.dbibih@gmail.com", result.getEmail());
        assertEquals(Role.PRESTATAIRE, result.getRole());
        verify(userRepository, times(1)).existsByEmail(req.email());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toDTO(any(User.class));
    }

    @Test
    public void testCreateUserWithExistingEmail() {
        // Arrange
        CreateUserREQ req = new CreateUserREQ("Marouane Dbibih", "marouane.dbibih@gmail.com", "password123", "DEMANDEUR");

        when(userRepository.existsByEmail(req.email())).thenReturn(true); // Email already taken

        // Act & Assert
        OwnAlreadyExistsException exception = assertThrows(OwnAlreadyExistsException.class, () -> {
            userService.create(req);
        });

        assertEquals("The email marouane.dbibih@gmail.com already exists", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(req.email());
        verify(userRepository, never()).save(any(User.class)); // Ensure save was not called
    }
}
