package org._iir.backend.mail.controllers;

import org._iir.backend.service.UserService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    private  String REDIRECT = "redirect:/login";


    @GetMapping("/verify")
    public String verifyUser(@RequestParam String token, RedirectAttributes attributes) {
        if (StringUtils.isEmpty(token)) {
            attributes.addFlashAttribute("error", "Invalid token");
            return REDIRECT;
        }
        try {
            userService.verifyAccount(token);

        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return REDIRECT;
        }
        attributes.addFlashAttribute("message", "Account verified successfully");
        return REDIRECT;
        }

}
