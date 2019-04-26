package de.digiwill.controller;

import de.digiwill.model.UserHandle;
import de.digiwill.service.UserHandleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class SendLifeSignController {

    @Autowired
    private UserHandleManager userHandleManager;

    @PostMapping("/lifeSign")
    public String sendLifeSign(Principal principal) {
        String emailAddress = principal.getName();
        UserHandle userHandle = userHandleManager.loadUserByEmailAddress(emailAddress);
        userHandle.sendSignOfLife();
        userHandleManager.updateUser(userHandle);
        return "index";
    }

}
