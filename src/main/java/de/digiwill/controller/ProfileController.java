package de.digiwill.controller;

import de.digiwill.model.UserHandle;
import de.digiwill.service.UserHandleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private UserHandleManager userHandleManager;

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        String emailAddress = principal.getName();
        UserHandle user = userHandleManager.loadUserByEmailAddress(emailAddress);
        user.getPersonalData();
        model.addAttribute("email", emailAddress);
        model.addAttribute("personalData", user.getPersonalData());
        return "profile";
    }
}
