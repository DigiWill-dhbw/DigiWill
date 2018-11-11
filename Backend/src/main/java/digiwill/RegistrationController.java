package digiwill;

import org.springframework.web.bind.annotation.*;

public class RegistrationController {

    @RequestMapping("/register")
    @ResponseBody
    public String register(@ModelAttribute("newUser") User newUser ) { //TODO implement
        if(newUser.isValidNewUser()){

        }
        //System.out.println(loginData.getUsername());
        //System.out.println(loginData.getPasswordHash());
        return "Hello";
    }
}
