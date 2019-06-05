package de.digiwill.controller;

import de.digiwill.service.validation.ValidationResponse;
import de.digiwill.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
public class RegisterController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String register(@RequestBody MultiValueMap<String, String> formData, Model model, RedirectAttributes redirectAttrs) {

        ValidationResponse response = registrationService.addNewUser(formData);
        response.adjustModel(model);
        return response.getRedirectTarget("register");
    }

    @GetMapping("/register")
    public String register(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        model.addAttribute("hasToast", false);
        model.addAttribute("responseText", "No error occured");
        return "register";
    }
}
