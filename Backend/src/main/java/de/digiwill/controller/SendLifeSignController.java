package de.digiwill.controller;

import de.digiwill.model.UserHandle;
import de.digiwill.repository.UserHandleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Date;

@Controller
public class SendLifeSignController {

    @Autowired
    UserHandleRepository repository;

    @PostMapping("/lifesign")
    void sendLifeSign(Principal principal) {
        String username = principal.getName();
        UserHandle userHandle = repository.findUserHandleByUsername(username);
        long currentTime = new Date().getTime();
        userHandle.setLastSignOfLife(currentTime);
        repository.save(userHandle);
    }

}
