package de.digiwill.controller;

import de.digiwill.model.UserHandle;
import org.springframework.web.bind.annotation.*;

public class RegistrationController {

    @RequestMapping("/register")
    @ResponseBody
    public String register(@ModelAttribute("newUser") UserHandle newUserHandle) { //TODO implement
        if(newUserHandle.isValidNewUser()){

        }
        //System.out.println(loginData.getUsername());
        //System.out.println(loginData.getPasswordHash());
        return "Hello";
    }
}
