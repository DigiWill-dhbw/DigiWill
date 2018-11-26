package de.digiwill.controller;

import de.digiwill.LoginData;
import de.digiwill.repository.UserHandleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public class LoginController {

    @Autowired
    UserHandleRepository repository;

    @RequestMapping("/login")
    @ResponseBody
    public String login(@ModelAttribute("loginData") LoginData loginData ) { //TODO implement
        //System.out.println(loginData.getUsername());
        //System.out.println(loginData.getPasswordHash());
        return "Hello";
    }

    @RequestMapping("/reset_pw")
    @ResponseBody
    public String reset_pw(@ModelAttribute("loginData") String emailAddress ) { //TODO implement
        return "reset_pw";
    }

}
