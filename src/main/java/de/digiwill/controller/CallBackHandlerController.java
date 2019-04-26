package de.digiwill.controller;

import de.digiwill.service.callback.CallbackResponse;
import de.digiwill.service.callback.CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CallBackHandlerController {

    @Autowired
    private CallbackService callbackService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "id") String id,
                           @RequestParam(name = "token") String token, Model model) {
        //should there be an extra error if id or token are missing?
        CallbackResponse response = callbackService.getCallbackResponse(id, token);
        response.adjustModel(model);

        return response.getRedirectTarget();
    }

}
