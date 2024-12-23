package org._iir.backend.modules.auth.mail.controllers;



import org._iir.backend.modules.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private AuthService authService;

    private  String REDIRECT = "redirect:/login";


    @GetMapping("/verify")
    public String verifyUser(@RequestParam String token, RedirectAttributes attributes) {
        if (StringUtils.isEmpty(token)) {
            attributes.addFlashAttribute("error", "Invalid token");
            return REDIRECT;
        }
        try {
            authService.verifyAccount(token);

        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return REDIRECT;
        }
        attributes.addFlashAttribute("message", "Account verified successfully");
        return REDIRECT;
        }

}
