package de.digiwill.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class WebhookController {
    @RequestMapping(value="/webhook", method = RequestMethod.POST)
    public String updateWebhook(@RequestBody MultiValueMap<String, String> formData, Model model) {
        return "index";
    }

    public String webhook(Model model, Principal principal) {
        return "webhook";
    }

}
