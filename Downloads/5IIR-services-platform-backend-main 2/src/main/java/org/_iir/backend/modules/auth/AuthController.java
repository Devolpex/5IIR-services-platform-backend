package org._iir.backend.modules.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org._iir.backend.exception.UserAleradyExistException;
import org._iir.backend.modules.auth.mail.error.RegistrationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest) {
        UserDTO user = authService.login(loginRequest);
        logger.info("User " + user.getEmail() + " logged in");
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request, BindingResult result) {
        // Validate the input
        if (result.hasErrors()) {
            // Collect validation errors into a list
            List<RegistrationError> errors = result.getFieldErrors().stream()
                    .map(error -> new RegistrationError(error.getField(), error.getDefaultMessage()))
                    .collect(Collectors.toList());
            // Return validation errors in the response body as JSON
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            // Attempt registration
            authService.register(request);
            return ResponseEntity.ok("User registered successfully");
        } catch (UserAleradyExistException e) {
            // Handle user already exists exception
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
