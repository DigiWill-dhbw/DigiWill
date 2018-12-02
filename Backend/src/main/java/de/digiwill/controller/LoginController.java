package de.digiwill.controller;

import de.digiwill.LoginData;
import de.digiwill.repository.UserHandleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public class LoginController {

    @RequestMapping("/reset_pw")
    @ResponseBody
    public String reset_pw(@ModelAttribute("loginData") String emailAddress ) { //TODO implement
        return "reset_pw";
    }

}
