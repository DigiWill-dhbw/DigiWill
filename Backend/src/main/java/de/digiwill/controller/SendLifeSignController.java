package de.digiwill.controller;

import de.digiwill.model.UserHandle;
import de.digiwill.model.UserHandleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.Instant;

@Controller
public class SendLifeSignController {

    @Autowired
    UserHandleManager userHandleManager;

    @PostMapping("/lifeSign")
    String sendLifeSign(Principal principal) {
        String username = principal.getName();
        UserHandle userHandle = userHandleManager.loadUserByUserName(username);
        long currentTime = Instant.now().getEpochSecond();
        userHandle.setLastSignOfLife(currentTime);
        userHandleManager.updateUser(userHandle);
        return "index";
    }

}
