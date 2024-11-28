package org._iir.backend.account;

import org._iir.backend.exception.PasswordIncorrectException;
import org._iir.backend.modules.account.AccountDTO;
import org._iir.backend.modules.account.AccountService;
import org._iir.backend.modules.account.InfoREQ;
import org._iir.backend.modules.account.PasswordREQ;
import org._iir.backend.modules.user.Role;
import org._iir.backend.modules.user.User;
import org._iir.backend.modules.user.UserRepository;
import org._iir.backend.modules.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a mock user
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setNom("John Doe");
        mockUser.setEmail("john.doe@example.com");
        mockUser.setPassword("hashedPassword");
        mockUser.setRole(Role.DEMANDEUR);
    }

    @Test
    void GetInfos() {
        // Mock the behavior
        when(userService.getAuthenticatedUser()).thenReturn(mockUser);

        // Call the service
        AccountDTO result = accountService.getInfos();

        // Assertions
        assertNotNull(result);
        assertEquals(mockUser.getId(), result.getId());
        assertEquals(mockUser.getNom(), result.getNom());
        assertEquals(mockUser.getEmail(), result.getEmail());
        assertEquals(mockUser.getRole(), result.getRole());

        // Verify interactions
        verify(userService, times(1)).getAuthenticatedUser();
    }

    @Test
    void UpdateInfos() {
        // Prepare request data
        InfoREQ req = new InfoREQ("Jane Doe", "jane.doe@example.com");

        // Mock the behavior
        when(userService.getAuthenticatedUser()).thenReturn(mockUser);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Call the service
        AccountDTO result = accountService.updateInfos(req);

        // Assertions
        assertNotNull(result);
        assertEquals(req.nom(), result.getNom());
        assertEquals(req.email(), result.getEmail());

        // Verify interactions
        verify(userService, times(1)).getAuthenticatedUser();
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void UpdatePassword_Success() {
        // Prepare request data
        PasswordREQ req = new PasswordREQ("oldPassword", "newPassword");

        // Mock the behavior
        when(userService.getAuthenticatedUser()).thenReturn(mockUser);
        when(passwordEncoder.matches(req.oldPassword(), mockUser.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(req.newPassword())).thenReturn("hashedPassword");

        // Call the service
        assertDoesNotThrow(() -> accountService.updatePassword(req));

        // Verify that the password was updated
        verify(passwordEncoder, times(1)).matches(req.oldPassword(), mockUser.getPassword());
        verify(passwordEncoder, times(1)).encode(req.newPassword());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void UpdatePassword_Failure() {
        // Prepare request data
        PasswordREQ req = new PasswordREQ("wrongOldPassword", "newPassword");

        // Mock the behavior
        when(userService.getAuthenticatedUser()).thenReturn(mockUser);
        when(passwordEncoder.matches(req.oldPassword(), mockUser.getPassword())).thenReturn(false);

        // Call the service and assert exception
        PasswordIncorrectException exception = assertThrows(PasswordIncorrectException.class,
                () -> accountService.updatePassword(req));
        assertEquals("Old password is incorrect", exception.getResponse().getErrors().get(0).getMessage());
        assertEquals("oldPassword", exception.getResponse().getErrors().get(0).getField());

        // Verify no interactions with password encoding or saving
        verify(passwordEncoder, times(1)).matches(req.oldPassword(), mockUser.getPassword());
        verify(passwordEncoder, times(0)).encode(anyString());
        verify(userRepository, times(0)).save(any(User.class));
    }
}
