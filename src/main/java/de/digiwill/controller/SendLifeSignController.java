package de.digiwill.controller;

import de.digiwill.model.UserHandle;
import de.digiwill.service.UserHandleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class SendLifeSignController {

    @Autowired
    private UserHandleManager userHandleManager;

    @PostMapping("/lifeSign")
    public String sendLifeSign(Principal principal, Model model) {
        String emailAddress = principal.getName();
        UserHandle userHandle = userHandleManager.loadUserByEmailAddress(emailAddress);
        userHandle.sendSignOfLife();
        userHandleManager.updateUser(userHandle);
        model.addAttribute("hasToast", true);
        model.addAttribute("errorText", "We registered your sign of life");
        model.addAttribute("errorLbl", "✔");
        return "index";
    }

}
