package org._iir.backend.ws;


import jakarta.validation.Valid;
import org._iir.backend.bean.User;
import org._iir.backend.exception.UserAleradyExistException;
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
@RestController
@RequestMapping("/api/users")
public class UserWs {

        @Autowired
        private UserService userService;

        @PostMapping("/register")
        public ResponseEntity<String> registerUser(@Valid @RequestBody User user, BindingResult result) {
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Invalid input");
            }

            try {
                userService.register(user);
                return ResponseEntity.ok("User registered successfully");
            } catch (UserAleradyExistException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }

}
}
