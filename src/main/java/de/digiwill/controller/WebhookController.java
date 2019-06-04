package de.digiwill.controller;


import de.digiwill.model.UserHandle;
import de.digiwill.model.WebhookAction;
import de.digiwill.service.UserHandleManager;
import de.digiwill.util.RegexMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;


@Controller
public class WebhookController {

    @Autowired
    private UserHandleManager userHandleManager;

    private Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @GetMapping(value="/webhook")
    public String webhook(Model model, Principal principal) {
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        model.addAttribute("webhook", user.getWebhookAction());
        return "webhook";
    }

    @PostMapping(value="/webhook")
    public RedirectView updateWebhook(@RequestParam(name = "apiKey", required = true) String apiKey, @RequestParam(name=
            "eventNames", required = true) String eventNames,
                                      Model model, Principal principal) {
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        WebhookAction webhook = new WebhookAction(apiKey);
        String[] events = eventNames.split(";");
        for (int i = 0; i<events.length; i++) {
            if(!events[i].equals("")) {
                webhook.addEvent(events[i]);
            }
        }
        int idx = user.getWebhookActionIdx();
        if(idx == -1) {
            user.addAction(webhook);
            userHandleManager.updateUser(user);
        } else {
            user.replaceAction(idx, webhook);
        }
        return new RedirectView("webhook");
    }
    @PostMapping(value="/deleteWebhook")
    public RedirectView deleteWebhook(Model model,
                                Principal principal) {
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        int idx = user.getWebhookActionIdx();
        if (idx != -1) {
            user.removeAction(idx);
            userHandleManager.updateUser(user);
        }
        return new RedirectView("webhook");
    }
}
