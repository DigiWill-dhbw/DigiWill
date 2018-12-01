package de.digiwill.controller;

import de.digiwill.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class RegisterController {

    @Autowired
    RegistrationService registrationService;

    Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @RequestMapping(value = "/registerNewUser", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String register(@RequestBody MultiValueMap<String, String> formData, Model model, RedirectAttributes redirectAttrs) {

        int returnCode = registrationService.addNewUser(formData);

        if (registrationService.wasRegistrationSuccesful(returnCode)) {
            model.addAttribute("name", formData.getFirst("firstName"));
            return "home";
        } else {
            model.addAttribute("failure", true);
            model.addAttribute("responseText", registrationService.codeToText(returnCode));
            return "register";
        }

    }
}
