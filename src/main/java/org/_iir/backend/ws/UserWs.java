package org._iir.backend.ws;


import jakarta.validation.Valid;
import org._iir.backend.bean.User;
import org._iir.backend.error.RegistrationError;
import org._iir.backend.exception.UserAleradyExistException;
import org._iir.backend.http.RegistrationRequest;
import org._iir.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserWs {

        @Autowired
        private UserService userService;




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
            userService.register(request);
            return ResponseEntity.ok("User registered successfully");
        } catch (UserAleradyExistException e) {
            // Handle user already exists exception
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
