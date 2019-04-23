package de.digiwill.controller;

import de.digiwill.model.EmailResponseHandle;
import de.digiwill.model.EmailVerificationHandle;
import de.digiwill.model.UserHandleManager;
import de.digiwill.repository.EmailResponseHandleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CallBackHandlerController {

    @Autowired
    private UserHandleManager userHandleManager;
    @Autowired
    private EmailResponseHandleRepository emailResponseHandleRepository;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "id", required = true) String id,
                           @RequestParam(name = "token", required = true) String token, Model model) {
        String result = "error";

        EmailResponseHandle emailResponseHandle = emailResponseHandleRepository.findEmailResponseHandleBy(new ObjectId(id));
        if(emailResponseHandle.verifyToken(token)){
            ((EmailVerificationHandle) emailResponseHandle).executeCallback(userHandleManager, emailResponseHandleRepository);
            result = "index";
        }

        return result;
    }

}
