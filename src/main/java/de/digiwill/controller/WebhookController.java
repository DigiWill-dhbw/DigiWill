package de.digiwill.controller;


import de.digiwill.model.BaseAction;
import de.digiwill.model.EmailAction;
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
import java.util.ArrayList;
import java.util.List;


@Controller
public class WebhookController {

    @Autowired
    private UserHandleManager userHandleManager;

    private Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @GetMapping(value="/webhook")
    public String webhook(Model model, Principal principal) {
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        model.addAttribute("webhook", user.getWebhook());
        return "webhook";
    }

    @PostMapping(value="/webhook")
    public RedirectView updateWebhook(@RequestParam(name = "url", required = true) String url, Model model, Principal principal) {
        logger.info(url);
        if (RegexMatcher.isIFTTTUrl(url)) {
            UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
            WebhookAction webhookAction = new WebhookAction(url);
            int idx = user.getWebhookActionIdx();
            if(idx == -1) {
                user.addAction(webhookAction);
            } else {
                user.replaceAction(idx, webhookAction);
            }
            userHandleManager.updateUser(user);
            model.addAttribute("hasToast", false);
            model.addAttribute("webhook", user.getWebhook());
        } else {
            model.addAttribute("hasToast", true);
            model.addAttribute("responseText", "Not valid IFTTT webhook URL");
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
