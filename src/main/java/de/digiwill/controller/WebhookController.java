package de.digiwill.controller;


import de.digiwill.model.UserHandle;
import de.digiwill.model.WebhookAction;
import de.digiwill.service.UserHandleManager;
import de.digiwill.util.RegexMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;


@Controller
public class WebhookController {

    private static final String WEBHOOK_EDIT_URL = "webhook";

    @Autowired
    private UserHandleManager userHandleManager;

    @GetMapping(value="/webhook")
    public String webhook(Model model, Principal principal) {
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        model.addAttribute("webhookAction", user.getWebhookAction());
        return "webhook";
    }

    @PostMapping(value="/webhook")
    public RedirectView updateWebhook(@RequestParam(name = "url", required = true) String url, Model model, Principal principal) {
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
            model.addAttribute("webhookAction", user.getWebhookAction());
        } else {
            model.addAttribute("hasToast", true);
            model.addAttribute("responseText", "Not valid IFTTT webhook URL");
        }
        return new RedirectView(WEBHOOK_EDIT_URL);
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
        return new RedirectView(WEBHOOK_EDIT_URL);
    }
}
