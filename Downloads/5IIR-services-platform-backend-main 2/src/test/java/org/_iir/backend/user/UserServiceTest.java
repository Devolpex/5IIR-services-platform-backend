package org._iir.backend.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org._iir.backend.modules.user.*;
import org._iir.backend.exception.OwnAlreadyExistsException;
import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.prestataire.Prestataire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public void CreateUserWithDemandeurRole() {
        // Arrange
        CreateUserREQ req = new CreateUserREQ("Marouane Dbibih", "marouane.dbibih@gmail.com", "password123",
                "DEMANDEUR");

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
    public void CreateUserWithPrestataireRole() {
        // Arrange
        CreateUserREQ req = new CreateUserREQ("Marouane Dbibih", "marouane.dbibih@gmail.com", "password123",
                "PRESTATAIRE");

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
    public void CreateUserWithExistingEmail() {
        // Arrange
        CreateUserREQ req = new CreateUserREQ("Marouane Dbibih", "marouane.dbibih@gmail.com", "password123",
                "DEMANDEUR");

        when(userRepository.existsByEmail(req.email())).thenReturn(true); // Email already taken

        // Act & Assert
        OwnAlreadyExistsException exception = assertThrows(OwnAlreadyExistsException.class, () -> {
            userService.create(req);
        });

        assertEquals("The email marouane.dbibih@gmail.com already exists", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(req.email());
        verify(userRepository, never()).save(any(User.class)); // Ensure save was not called
    }

    @Test
    void Delete_UserExists() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(userId);

        verify(userRepository).delete(user);
    }

    @Test
    void Delete_UserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.delete(userId));
        assertEquals("User with id 1 not found", exception.getMessage());
    }

    @Test
    void FindById_UserExists() {
        Long userId = 1L;
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(userDTO, result);
    }

    @Test
    void FindById_UserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.findById(userId));
        assertEquals("User with id 1 not found", exception.getMessage());
    }

    @Test
    void FindList() {
        List<User> users = List.of(new User(), new User());
        List<UserDTO> userDTOs = List.of(new UserDTO(), new UserDTO());

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        List<UserDTO> result = userService.findList();

        assertNotNull(result);
        assertEquals(userDTOs.size(), result.size());
        verify(userRepository).findAll();
    }

    @Test
    void FindPage() {
        List<User> users = List.of(new User(), new User());
        Page<User> userPage = new PageImpl<>(users);
        Page<UserDTO> userDTOPage = new PageImpl<>(List.of(new UserDTO(), new UserDTO()));
        Pageable pageable = PageRequest.of(0, 2);

        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        Page<UserDTO> result = userService.findPage(pageable);

        assertNotNull(result);
        assertEquals(userDTOPage.getTotalElements(), result.getTotalElements());
        verify(userRepository).findAll(pageable);
    }

    @Test
    void Search() {
        String keyword = "test";
        List<User> users = List.of(new User(), new User());
        Page<User> userPage = new PageImpl<>(users);
        Page<UserDTO> userDTOPage = new PageImpl<>(List.of(new UserDTO(), new UserDTO()));
        Pageable pageable = PageRequest.of(0, 2);

        when(userRepository.search(keyword, pageable)).thenReturn(userPage);
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        Page<UserDTO> result = userService.search(keyword, pageable);

        assertNotNull(result);
        assertEquals(userDTOPage.getTotalElements(), result.getTotalElements());
        verify(userRepository).search(keyword, pageable);
    }

    @Test
    void Update_UserExists() {
        // Arrange
        Long userId = 1L;
        UpdateUserREQ req = new UpdateUserREQ("Updated Name", "updated@example.com", "newpassword", "DEMANDEUR");
        User existingUser = User.builder()
                .id(userId)
                .nom("Old Name")
                .email("old@example.com")
                .password("oldpassword")
                .role(Role.DEMANDEUR)
                .build();
        User updatedUser = User.builder()
                .id(userId)
                .nom("Updated Name")
                .email("updated@example.com")
                .password("encodedpassword")
                .role(Role.DEMANDEUR)
                .build();
        UserDTO userDTO = UserDTO.builder()
                .id(userId)
                .nom("Updated Name")
                .email("updated@example.com")
                .role(Role.DEMANDEUR)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Update the existingUser object with new data
        existingUser.setNom(req.nom());
        existingUser.setEmail(req.email());
        existingUser.setPassword("encodedpassword");

        when(userRepository.save(existingUser)).thenReturn(updatedUser); // Mock save with updated user
        when(userMapper.toDTO(updatedUser)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.update(req, userId);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getNom());
        assertEquals("updated@example.com", result.getEmail());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser); // Ensure save is called
        verify(userMapper, times(1)).toDTO(updatedUser); // Ensure toDTO is called
    }

    @Test
    void Update_UserNotFound() {
        // Arrange
        Long userId = 1L;
        UpdateUserREQ req = new UpdateUserREQ("Updated Name", "updated@example.com", "newpassword", "DEMANDEUR");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(OwnNotFoundException.class, () -> userService.update(req, userId));

        assertEquals("User with id 1 not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any());
    }
}
